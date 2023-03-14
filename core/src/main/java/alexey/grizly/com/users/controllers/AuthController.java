package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.properties.AppGlobalProperties;
import alexey.grizly.com.users.dto.request.AuthRequestDto;
import alexey.grizly.com.users.dto.response.AuthResponseDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.service.AuthService;
import alexey.grizly.com.users.service.RefreshTokenService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtUtil;
    private final AppGlobalProperties properties;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtTokenUtil jwtUtil, AppGlobalProperties properties) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.properties = properties;
    }


    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody @Validated AuthRequestDto authRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }
        String errorMessage;
        try {
            UserAccount user = (UserAccount) authService.authentication(authRequest);
            return getAuthResponseDto(user);
        }catch (LockedException|DisabledException e){
            errorMessage="Аккаунт заблокирован. Обратитесь к администратору";
        }catch (CredentialsExpiredException e){
            errorMessage="Срок действия пароля истек. Обратитесь к администратору";
        }catch (BadCredentialsException e){
            errorMessage = "Не правильная почта или пароль";
        }
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }


    private ResponseEntity<?> getAuthResponseDto(UserAccount user) {
        Date issuedDate = new Date();
        Date accessExpires = new Date(issuedDate.getTime() + properties.getJwtLifetime());
        Date refreshExpire = new Date(issuedDate.getTime()+properties.getJwtRefreshLifetime());
        String accessToken = jwtUtil.generateToken(user.getAuthorities(), user.getEmail(), issuedDate,accessExpires);
        String refreshToken = jwtUtil.generateRefreshTokenFromEmail(user.getEmail(),refreshExpire,issuedDate);
        int resultSave = refreshTokenService.saveRefreshToken(user,refreshToken,refreshExpire);
        if(resultSave!=1){
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,"");
            return new ResponseEntity<>(errorDto,HttpStatus.UNAUTHORIZED);
        }
        AuthResponseDto dto =AuthResponseDto
                .builder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(dto);
    }

}
