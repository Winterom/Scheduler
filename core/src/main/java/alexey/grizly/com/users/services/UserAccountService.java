package alexey.grizly.com.users.services;

import alexey.grizly.com.users.models.UserAccount;

import java.time.LocalDateTime;
import java.util.Date;

public interface UserAccountService {
    Boolean generateAndSaveRestorePasswordToken(final Long userId, final LocalDateTime expire);

    UserAccount getSimpleUserAccount(final String email);

    boolean updatePassword(final String email, final String passwordHash, final String token);
    UserAccount registration();
}
