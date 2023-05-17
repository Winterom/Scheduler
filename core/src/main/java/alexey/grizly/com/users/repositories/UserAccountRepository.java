package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserAccountRepository {

    UserAccount getSimpleUserAccount(final String email);

    int saveRestorePasswordToken(final Long userId,
                                 final LocalDateTime expireDate,
                                 final String token);

    Long registrationNewUser(final String email,
                             final String phone,
                             final String passwordHash,
                             final LocalDateTime credentialExpired,
                             final EUserStatus status,
                             final LocalDateTime createdAt);
}
