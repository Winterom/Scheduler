package alexey.grizly.com.users.ws_handlers;

import alexey.grizly.com.users.dtos.response.UserProfileResponseDto;
import alexey.grizly.com.users.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ProfileWebsocketHandler extends TextWebSocketHandler {
    private final UserProfileService userProfileService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProfileWebsocketHandler(UserProfileService userProfileService, ObjectMapper objectMapper) {
        super();
        this.userProfileService = userProfileService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println(message);
        System.out.println(session.getPrincipal());
        UserProfileResponseDto responseDto = userProfileService.getProfileByEmail("winterom@gmail.com");
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseDto));
        session.sendMessage(response);
    }
}
