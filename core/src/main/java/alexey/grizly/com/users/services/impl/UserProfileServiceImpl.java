package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import alexey.grizly.com.users.services.UserPasswordService;
import alexey.grizly.com.users.services.UserProfileService;
import alexey.grizly.com.users.ws_handlers.WSResponseEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        UserProfileResponse response = userProfileRepository.getUserAccountWithRoles(email);
        ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
        responseMessage.setEvent(WSResponseEvents.PROFILE);
        responseMessage.setData(response);
        return responseMessage;
    }

    @Override
    public ResponseMessage<SecurityProperties.UserPasswordStrength> getPasswordStrength() {
        SecurityProperties.UserPasswordStrength response = securityProperties.getUserPasswordStrength();
        ResponseMessage<SecurityProperties.UserPasswordStrength> responseMessage =
                new ResponseMessage<>();
        responseMessage.setData(response);
        responseMessage.setEvent(WSResponseEvents.PASSWORD_STRENGTH);
        return responseMessage;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return userPasswordService.changePassword(email,password);
    }
}
