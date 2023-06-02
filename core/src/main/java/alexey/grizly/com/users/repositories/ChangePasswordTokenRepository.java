package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.user.UserAccountWithPasswordChangeToken;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChangePasswordTokenRepository {
    int saveChangePasswordToken(final Long userId,
                                final LocalDateTime expireDate,
                                final String token);
    UserAccountWithPasswordChangeToken isUserAccountHaveToken(final String email);

    void deleteUsedToken(Long id);
}
