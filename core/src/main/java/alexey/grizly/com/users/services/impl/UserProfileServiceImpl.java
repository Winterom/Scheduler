package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import alexey.grizly.com.users.services.UserPasswordService;
import alexey.grizly.com.users.services.UserProfileService;
import alexey.grizly.com.users.ws_handlers.WSResponseEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserPasswordService userPasswordService;
    private final UserProfileRepository userProfileRepository;
    private final SecurityProperties securityProperties;

    @Autowired
    public UserProfileServiceImpl(final UserPasswordService userPasswordService,
                                  final UserProfileRepository userProfileRepository,
                                  final SecurityProperties securityProperties) {
        this.userPasswordService = userPasswordService;
        this.userProfileRepository = userProfileRepository;
        this.securityProperties = securityProperties;
    }

    @Override
    public ResponseMessage<UserProfileResponse> getProfileByEmail(String email) {
        UserProfileResponse account = userProfileRepository.getUserAccountWithRoles(email);
        ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
        responseMessage.setEvent(WSResponseEvents.UPDATE_PROFILE);
        ResponseMessage.MessagePayload<UserProfileResponse> payload = new ResponseMessage.MessagePayload<>();
        if(account==null){
            payload.setErrorMessage(List.of("Пользователь с "+email+" не найден"));
            payload.setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
        }else {
            payload.setResponseStatus(ResponseMessage.ResponseStatus.OK);
            payload.setData(account);
        }
        responseMessage.setPayload(payload);
        return responseMessage;
    }

    @Override
    public ResponseMessage<PasswordStrengthResponseDto> getPasswordStrength() {
        SecurityProperties.UserPasswordStrength passwordStrength = securityProperties.getUserPasswordStrength();
        ResponseMessage<PasswordStrengthResponseDto> responseMessage =
                new ResponseMessage<>();
        ResponseMessage.MessagePayload<PasswordStrengthResponseDto> payload =
                new ResponseMessage.MessagePayload<>();
        responseMessage.setEvent(WSResponseEvents.PASSWORD_STRENGTH);
        if(passwordStrength==null){
            payload.setErrorMessage(List.of("Данные не доступны"));
            payload.setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
        }else {
            payload.setResponseStatus(ResponseMessage.ResponseStatus.OK);
            payload.setData(new PasswordStrengthResponseDto(passwordStrength));
        }
        responseMessage.setPayload(payload);
        return responseMessage;
    }

    @Override
    public ResponseMessage<UserProfileResponse> updatePassword(String email, String password) {
        List<String> errorMessage = userPasswordService.changePassword(email,password);
        if(!errorMessage.isEmpty()){
            ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
            ResponseMessage.MessagePayload<UserProfileResponse> payload = new ResponseMessage.MessagePayload<>();
            responseMessage.setEvent(WSResponseEvents.UPDATE_PROFILE);
            payload.setErrorMessage(errorMessage);
            payload.setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
            responseMessage.setPayload(payload);
            return responseMessage;
        }
       return this.getProfileByEmail(email);
    }
}
