package alexey.grizly.com.users.ws_handlers;

import alexey.grizly.com.users.dtos.response.UserProfileResponse;
import alexey.grizly.com.users.messages.RequestMessage;
import alexey.grizly.com.users.messages.ResponseMessage;
import alexey.grizly.com.users.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;

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
        String request1 = StringEscapeUtils.unescapeJson(message.getPayload());
        String request=String.copyValueOf(request1.toCharArray(),1,request1.length()-1);
        RequestMessage requestMessage = objectMapper.readValue(request, RequestMessage.class);
        Principal principal = session.getPrincipal();
        if (principal==null){
            session.close();
            return;
        }
        UserProfileResponse responseDto = userProfileService.getProfileByEmail(principal.getName());
        ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
        responseMessage.setEvent("profile");
        responseMessage.setData(responseDto);
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }
}
