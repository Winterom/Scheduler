package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.user.UserAccount;
import alexey.grizly.com.users.services.AuthenticationService;
import alexey.grizly.com.users.services.RefreshTokenService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtUtil;
    private final SecurityProperties properties;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtTokenUtil jwtUtil, SecurityProperties properties) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.properties = properties;
    }
    @Override
    public String authentication(String emailOrPhone, String password, HttpServletResponse response){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(emailOrPhone, password));
        UserAccount user = (UserAccount) authentication.getPrincipal();
        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtLifetime());
        String accessToken = jwtUtil.generateToken(user.getAuthorities(), user.getEmail(), issuedDate, expireDate, user.getId());
        refreshTokenService.generateRefreshToken(user,issuedDate,response);
        return accessToken;
    }

    @Override
    @Nullable
    public String authenticationByRefreshToken(Cookie cookie) {
        UserAccount userAccount = refreshTokenService.checkRefreshToken(cookie.getValue());
        if(userAccount==null){
            return null;
        }
        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtLifetime());
        return jwtUtil.generateToken(userAccount.getAuthorities(), userAccount.getEmail(), issuedDate, expireDate, userAccount.getId());
    }


}
