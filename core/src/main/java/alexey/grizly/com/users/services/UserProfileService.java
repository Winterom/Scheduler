package alexey.grizly.com.users.services;

import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    ResponseMessage<UserProfileResponse> getProfileByEmail(final String email);
    ResponseMessage<SecurityProperties.UserPasswordStrength> getPasswordStrength();
    boolean updatePassword(String email,String password);
}
