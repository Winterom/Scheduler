package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.users.dto.request.AuthRequestDto;
import alexey.grizly.com.users.dto.response.AuthResponseDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.properties.UserGlobalProperties;
import alexey.grizly.com.users.service.AuthService;
import alexey.grizly.com.users.service.RefreshTokenService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtUtil;
    private final UserGlobalProperties properties;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtTokenUtil jwtUtil, UserGlobalProperties properties) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.properties = properties;
    }


    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody @Validated AuthRequestDto authRequest, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            String errorMessage = "Неверные учетные данные пользователя";
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        UserAccount user = (UserAccount) authService.authentication(authRequest);
        Date issuedDate = new Date();
        Date accessExpires = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtLifetime());
        Date refreshExpire = new Date(issuedDate.getTime()+properties.getJwtProperties().getJwtRefreshLifetime());
        String accessToken = jwtUtil.generateToken(user.getAuthorities(), user.getEmail(), issuedDate,accessExpires);
        String refreshToken = jwtUtil.generateRefreshTokenFromEmail(user.getEmail(),refreshExpire,issuedDate);
        int resultSave = refreshTokenService.saveRefreshToken(user,refreshToken,refreshExpire);
        if(resultSave!=1){
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,"Неверные учетные данные пользователя");
            return new ResponseEntity<>(errorDto,HttpStatus.UNAUTHORIZED);
        }
        Cookie cookie = new Cookie("token",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(properties.getJwtProperties().getJwtRefreshLifetime());
        response.addCookie(cookie);
        AuthResponseDto dto = new AuthResponseDto(accessToken);
        return ResponseEntity.ok(dto);
    }



}
