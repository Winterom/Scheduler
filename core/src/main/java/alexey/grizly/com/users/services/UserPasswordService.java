package alexey.grizly.com.users.services;


import java.util.List;

public interface UserPasswordService {


    List<String> createChangePasswordToken(final String email);

    List<String> changePassword(String email, String password, String token);

    List<String> changePassword(String email, String notValidationPassword);
}
