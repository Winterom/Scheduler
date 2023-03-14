package alexey.grizly.com.users.service;

import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    @Autowired
    public RefreshTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public int saveRefreshToken(UserAccount user, String refreshToken, Date refreshExpire) {
        return userRepository.saveRefreshToken(user.getId(),refreshExpire,refreshToken);
    }
}
