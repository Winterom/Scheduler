package alexey.grizly.com.users.services;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;

import java.time.LocalDateTime;

public interface UserAccountService {
    void saveChangePasswordToken(final Long userId,
                                 final LocalDateTime expire,
                                 final String token);

    UserAccount getSimpleUserAccount(final String email);

    UserAccount getUserAccountByApprovedEmailToken(final String email,
                                                   final String token);
    int updateEmailVerifiedStatus(final UserAccount userAccount);

    int deleteUsedEmailApprovedToken(final UserAccount userAccount);

    void updatePassword(final UserAccount userAccount,
                        final String passwordHash,
                        LocalDateTime credentialExpired);
    void deleteUsedChangePasswordTokenByUserId(Long userId);
    UserAccount checkPasswordChangeToken (final String email, final String token);
    UserAccount createNewUserAccount(final String email,
                              final String phone,
                              final String passwordHash,
                              final LocalDateTime credentialExpired,
                              final EUserStatus status,
                              final LocalDateTime createdAt);
    int saveApprovedEmailToken(final Long userId, String token);

    boolean emailBusyCheck(final String email);

    boolean phoneBusyCheck(final String phone);
}
