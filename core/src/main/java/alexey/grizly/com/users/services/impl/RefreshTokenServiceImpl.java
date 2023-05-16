package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.repositories.AuthRepository;
import alexey.grizly.com.users.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final AuthRepository authRepository;
    @Autowired
    public RefreshTokenServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    @Override
    @Transactional
    public int saveRefreshToken(final Long userId, final String refreshToken, final Date refreshExpire) {
        return authRepository.saveRefreshToken(userId,refreshExpire,refreshToken);
    }
}
