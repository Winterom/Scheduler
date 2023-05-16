package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserAccountRepository {

    UserAccount getSimpleUserAccount(String email);

    int saveRestorePasswordToken(final Long userId, final LocalDateTime expireDate, final String token);
}
