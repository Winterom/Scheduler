package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChangePasswordTokenRepository {
    int saveChangePasswordToken(final Long userId,
                                final LocalDateTime expireDate,
                                final String token);
    UserAccount checkPasswordChangeToken(final String email,
                                         final String token);

    void deleteUsedToken(Long id);
}
