package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserAccountRepository {

    UserAccount getSimpleUserAccount(final String email);

    int updatePassword(final Long userId,
                       final String passwordHash,
                       final LocalDateTime credentialExpired);
    Long registrationNewUser(final String email,
                             final String phone,
                             final String passwordHash,
                             final LocalDateTime credentialExpired,
                             final EUserStatus status,
                             final LocalDateTime createdAt);

    Long countOfUsageEmail(String email);

    Long countOfUsagePhone(String phone);

    int setEmailVerifiedStatusByUserId(Long id,Boolean status);
}
