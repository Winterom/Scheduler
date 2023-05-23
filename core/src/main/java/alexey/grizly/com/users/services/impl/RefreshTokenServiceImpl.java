package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.repositories.RefreshTokenRepository;
import alexey.grizly.com.users.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    @Transactional
    public int saveRefreshToken(final Long userId, final String refreshToken, final Date refreshExpire) {
        return refreshTokenRepository.saveRefreshToken(userId,refreshExpire,refreshToken);
    }
}
