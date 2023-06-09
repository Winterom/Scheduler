package alexey.grizly.com.users.ws_handlers;

import alexey.grizly.com.commons.configs.ConnectionList;
import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.messages.request.RequestMessage;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;


@Component
public class ProfileWebsocketHandler extends TextWebSocketHandler {
    private final UserProfileService userProfileService;
    private final ObjectMapper objectMapper;
    private final ConnectionList connection;



    @Autowired
    public ProfileWebsocketHandler(final UserProfileService userProfileService,
                                   final ObjectMapper objectMapper,
                                   final ConnectionList connection) {
        super();
        this.userProfileService = userProfileService;
        this.objectMapper = objectMapper;
        this.connection = connection;
    }
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        Principal principal = session.getPrincipal();
        if(principal==null){
            return;
        }
        this.connection.deleteSession(principal.getName());
    }
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Principal principal = session.getPrincipal();
        if(principal==null){
            return;
        }
        connection.putSession(principal.getName(),session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        Principal principal = session.getPrincipal();
        if (principal==null){
            session.close();
            return;
        }
        String request1 = StringEscapeUtils.unescapeJson(message.getPayload());
        String request=String.copyValueOf(request1.toCharArray(),1,request1.length()-1);
        RequestMessage requestMessage = objectMapper.readValue(request, RequestMessage.class);
        switch (requestMessage.getEvent()){
            case GET_PROFILE ->getProfile(session, principal.getName());
            case PASSWORD_STRENGTH -> getPasswordStrength(session);
            case UPDATE_PASSWORD -> updatePassword(session,requestMessage);
        }
    }

    private void getProfile(WebSocketSession session, String email) throws IOException {
        ResponseMessage<UserProfileResponse> responseMessage = userProfileService.getProfileByEmail(email);
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    private void getPasswordStrength(WebSocketSession session) throws IOException {
        ResponseMessage<PasswordStrengthResponseDto> responseMessage =
                this.userProfileService.getPasswordStrength();
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    private void updatePassword(WebSocketSession session,RequestMessage requestMessage) throws IOException {
        String password = requestMessage.getData();
        ResponseMessage<UserProfileResponse> responseMessage =this.userProfileService.updatePassword(session.getPrincipal().getName(),password);
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

}
