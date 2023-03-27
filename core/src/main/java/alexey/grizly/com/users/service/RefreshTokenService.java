package alexey.grizly.com.users.service;

import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Transactional
    public int saveRefreshToken(UserAccount user, String refreshToken, Date refreshExpire) {
        return refreshTokenRepository.saveRefreshToken(user.getId(),refreshExpire,refreshToken);
    }
}
