package alexey.grizly.com.users.listeners;

import alexey.grizly.com.commons.configs.ConnectionList;
import alexey.grizly.com.users.events.UserAccountChangeEvent;
import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.profile.response.UserProfileResponse;
import alexey.grizly.com.users.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
@Slf4j
@Service
public class UserAccountChangeListener  implements ApplicationListener<UserAccountChangeEvent> {
    private final ConnectionList connectionList;
    private final UserProfileService userProfileService;
    private final ObjectMapper objectMapper;


    public UserAccountChangeListener(ConnectionList connectionList, UserProfileService userProfileService, ObjectMapper objectMapper) {
        this.connectionList = connectionList;
        this.userProfileService = userProfileService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onApplicationEvent(UserAccountChangeEvent event) {
        WebSocketSession session = connectionList.getSession(event.getEmail());
        if(session==null){
            return;
        }
        ResponseMessage<UserProfileResponse> responseMessage=userProfileService.getProfileByEmail(event.getEmail());
        try {
            TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
            session.sendMessage(response);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }
}
