package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.dtos.request.AuthRequestDto;
import alexey.grizly.com.users.dtos.response.AuthResponseDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.AuthService;
import alexey.grizly.com.users.services.RefreshTokenService;
import alexey.grizly.com.users.services.impl.RefreshTokenServiceImpl;
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

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtUtil;
    private final SecurityProperties properties;
    private final GlobalProperties globalProperties;

    public AuthController(final AuthService authService, final RefreshTokenServiceImpl refreshTokenService, final JwtTokenUtil jwtUtil, final SecurityProperties properties, final GlobalProperties globalProperties) {
    this.authService = authService;
    this.refreshTokenService = refreshTokenService;
    this.jwtUtil = jwtUtil;
    this.properties = properties;
    this.globalProperties = globalProperties;
    }
    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody @Validated final AuthRequestDto authRequest, BindingResult bindingResult, HttpServletResponse response) {
        /*TODO переделать на jakarta.validation*/
        if (bindingResult.hasErrors()) {
            String errorMessage = "Неверные учетные данные пользователя";
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED, errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        UserAccount user = (UserAccount) authService.authentication(authRequest.getEmailOrPhone(),authRequest.getPassword());
        Date issuedDate = new Date();
        Date accessExpires = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtLifetime());
        Date refreshExpire = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtRefreshLifetime());
        String accessToken = jwtUtil.generateToken(user.getAuthorities(), user.getEmail(), issuedDate, accessExpires, user.getId());
        String refreshToken = jwtUtil.generateRefreshTokenFromEmail(user.getEmail(), refreshExpire, issuedDate);
        int resultSave = refreshTokenService.saveRefreshToken(user.getId(), refreshToken, refreshExpire);
        if (resultSave != 1) {
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED, "Неверные учетные данные пользователя");
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        Cookie cookie = new Cookie("token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(properties.getJwtProperties().getJwtRefreshLifetime());
        response.addCookie(cookie);
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(globalProperties.getTimeZone());
        String expire = isoFormat.format(accessExpires);
        AuthResponseDto dto = new AuthResponseDto(accessToken, expire);
        return ResponseEntity.ok(dto);
    }


}
