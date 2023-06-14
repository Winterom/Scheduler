package alexey.grizly.com.users.services;

import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;

import alexey.grizly.com.users.messages.response.CheckBusyPhoneOrEmail;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.SendVerifyToken;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import org.springframework.stereotype.Service;


@Service
public interface UserProfileService {

    ResponseMessage<UserProfileResponse> getProfileByEmail(final String email);
    ResponseMessage<PasswordStrengthResponseDto> getPasswordStrength();
    ResponseMessage<UserProfileResponse> updatePassword(final String email, final String password);
    ResponseMessage<UserProfileResponse> updateProfile(final String oldEmail, final String email,final String phone);
    ResponseMessage<CheckBusyPhoneOrEmail> checkEmail(String email);
    ResponseMessage<CheckBusyPhoneOrEmail> checkPhone(String phone);
    ResponseMessage<SendVerifyToken> sendEmailVerifyToken(String email);
}
