package alexey.grizly.com.users.services;

import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;

import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import org.springframework.stereotype.Service;


@Service
public interface UserProfileService {

    ResponseMessage<UserProfileResponse> getProfileByEmail(final String email);
    ResponseMessage<PasswordStrengthResponseDto> getPasswordStrength();
    ResponseMessage<UserProfileResponse> updatePassword(String email, String password);
}
