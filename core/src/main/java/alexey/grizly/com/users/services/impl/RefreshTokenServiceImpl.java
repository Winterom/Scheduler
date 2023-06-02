package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.user.UserAccount;
import alexey.grizly.com.users.repositories.RefreshTokenRepository;
import alexey.grizly.com.users.services.RefreshTokenService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecurityProperties properties;
    private final JwtTokenUtil jwtUtil;
    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, SecurityProperties properties, JwtTokenUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.properties = properties;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void generateRefreshToken(final UserAccount userAccount, final Date issuedDate, final HttpServletResponse response){
        Date refreshExpire = new Date(issuedDate.getTime() + properties.getJwtProperties().getJwtRefreshLifetime());
        String refreshToken = jwtUtil.generateRefreshTokenFromEmail(userAccount.getEmail());
        refreshTokenRepository.saveRefreshToken(userAccount.getId(), refreshExpire, refreshToken);
        response.addCookie(createRefreshTokenCookie(refreshToken));
    }

    @Override
    public UserAccount checkRefreshToken(String refreshToken){
        return this.refreshTokenRepository.checkRefreshToken(refreshToken);
    }
    private @NotNull Cookie createRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(properties.getJwtProperties().getJwtRefreshLifetime());
        return cookie;
    }


}
