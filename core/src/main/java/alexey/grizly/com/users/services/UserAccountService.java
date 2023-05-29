package alexey.grizly.com.users.services;


import alexey.grizly.com.users.models.UserAccount;

import java.time.LocalDateTime;

public interface UserAccountService {
    void saveChangePasswordToken(final Long userId,
                                 final LocalDateTime expire,
                                 final String token);

    UserAccount getSimpleUserAccount(final String email);


    void updatePassword(final UserAccount userAccount,
                        final String passwordHash,
                        LocalDateTime credentialExpired);
    void deleteUsedChangePasswordTokenByUserId(Long userId);
    UserAccount checkPasswordChangeToken (final String email, final String token);


    boolean emailBusyCheck(final String email);

    boolean phoneBusyCheck(final String phone);
}
