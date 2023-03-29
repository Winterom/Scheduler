package alexey.grizly.com.users.services;

import alexey.grizly.com.users.models.UserAccount;

public interface UserPasswordService {
    String generateAndSaveRestorePasswordToken(UserAccount userAccount);

    UserAccount getSimpleUserAccount(String email);

    boolean updatePassword(String email, String passwordHash, String token);
}
