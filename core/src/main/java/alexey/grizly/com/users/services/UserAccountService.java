package alexey.grizly.com.users.services;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;

import java.time.LocalDateTime;

public interface UserAccountService {
    void saveRestorePasswordToken(final Long userId,
                                  final LocalDateTime expire,
                                  final String token);

    UserAccount getSimpleUserAccount(final String email);

    boolean updatePassword(final String email, final String passwordHash, final String token);
    Long createNewUserAccount(final String email,
                              final String phone,
                              final String passwordHash,
                              final LocalDateTime credentialExpired,
                              final EUserStatus status,
                              final LocalDateTime createdAt);
}
