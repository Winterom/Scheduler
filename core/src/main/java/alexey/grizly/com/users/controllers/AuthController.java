package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.properties.AppGlobalProperties;
import alexey.grizly.com.users.dto.request.AuthRequestDto;
import alexey.grizly.com.users.dto.response.AuthResponseDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.service.RefreshTokenService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserDetailsService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtUtil;
    private final AppGlobalProperties properties;

    public AuthController(UserDetailsService authService, RefreshTokenService refreshTokenService, JwtTokenUtil jwtUtil, AppGlobalProperties properties) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.properties = properties;
    }


    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody AuthRequestDto authRequest){
        UserAccount user = (UserAccount) authService.loadUserByUsername(authRequest.getEmail());
        return getAuthResponseDto(user);
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
                .setSession(UUID.randomUUID().toString())
                .build();
        return ResponseEntity.ok(dto);
    }
}
