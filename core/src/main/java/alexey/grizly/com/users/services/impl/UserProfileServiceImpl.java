package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import alexey.grizly.com.users.services.UserProfileService;
import alexey.grizly.com.users.ws_handlers.WSREsponseEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    public final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public ResponseMessage<UserProfileResponse> getProfileByEmail(String email) {
        UserProfileResponse response = userProfileRepository.getUserAccountWithRoles(email);
        ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
        responseMessage.setEvent(WSREsponseEvents.PROFILE);
        responseMessage.setData(response);
        return responseMessage;
    }
}
