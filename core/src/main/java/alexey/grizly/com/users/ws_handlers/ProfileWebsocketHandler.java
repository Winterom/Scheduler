package alexey.grizly.com.users.ws_handlers;

import alexey.grizly.com.commons.configs.ConnectionList;
import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;
import alexey.grizly.com.users.messages.request.UpdateProfileRequestMessage;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.messages.request.RequestMessage;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.services.UserProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;


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
    public void afterConnectionClosed(@NotNull final WebSocketSession session, @NotNull final CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        Principal principal = session.getPrincipal();
        if(principal==null){
            return;
        }
        this.connection.deleteSession(principal.getName());
    }
    @Override
    public void afterConnectionEstablished(@NotNull final WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Principal principal = session.getPrincipal();
        if(principal==null){
            return;
        }
        connection.putSession(principal.getName(),session);
    }

    @Override
    protected void handleTextMessage(@NotNull final WebSocketSession session, @NotNull final TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        Principal principal = session.getPrincipal();
        if (principal==null){
            session.close();
            return;
        }
        String request1 = StringEscapeUtils.unescapeJson(message.getPayload());
        String request=String.copyValueOf(request1.toCharArray(),1,request1.length()-2);
        RequestMessage requestMessage = objectMapper.readValue(request, RequestMessage.class);
        switch (requestMessage.getEvent()){
            case GET_PROFILE ->getProfile(session, principal.getName());
            case PASSWORD_STRENGTH -> getPasswordStrength(session);
            case UPDATE_PASSWORD -> updatePassword(session,requestMessage);
            case UPDATE_PROFILE -> updateProfile(session,requestMessage);
            case CHECK_EMAIL_BUSY -> checkEmailBusy(session,requestMessage);
            case CHECK_PHONE_BUSY -> checkPhoneBusy(session,requestMessage);
            default -> unknownEvent(session);
        }
    }

    private void getProfile(final WebSocketSession session, final String email) throws IOException {
        ResponseMessage<UserProfileResponse> responseMessage = userProfileService.getProfileByEmail(email);
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    private void getPasswordStrength(final WebSocketSession session) throws IOException {
        ResponseMessage<PasswordStrengthResponseDto> responseMessage =
                this.userProfileService.getPasswordStrength();
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    private void updatePassword(final WebSocketSession session,final RequestMessage requestMessage) throws IOException {
        String email = session.getPrincipal().getName();
        String password = requestMessage.getData();
        ResponseMessage<UserProfileResponse> responseMessage =this.userProfileService.updatePassword(email,password);
        if(responseMessage.getPayload().getResponseStatus().equals(ResponseMessage.ResponseStatus.OK)){
            this.getProfile(session,email);
        }
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    private void updateProfile(final WebSocketSession session,final RequestMessage requestMessage) throws IOException {
        String email = session.getPrincipal().getName();
        String data = requestMessage.getData();
        UpdateProfileRequestMessage message;
        try {
            message = objectMapper.readValue(data, UpdateProfileRequestMessage.class);
        }catch (JsonProcessingException exception){
           ResponseMessage<UserProfileResponse> responseMessage = new ResponseMessage<>();
           responseMessage.setEvent(EWebsocketEvents.UPDATE_PROFILE);
           ResponseMessage.MessagePayload<UserProfileResponse> payload = new ResponseMessage.MessagePayload<>();
           payload.setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
           payload.setErrorMessages(List.of(exception.getMessage()));
           responseMessage.setPayload(payload);
           TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
           session.sendMessage(response);
           return;
        }
        ResponseMessage<UserProfileResponse> responseMessage
                =this.userProfileService.updateProfile(email, message.getEmail(),message.getPhone());
        TextMessage response = new TextMessage(objectMapper.writeValueAsBytes(responseMessage));
        session.sendMessage(response);
    }

    public void unknownEvent(final WebSocketSession session) throws IOException {
        session.close();
    }

    private void checkEmailBusy(final WebSocketSession session,final RequestMessage requestMessage){
        String oldEmail = session.getPrincipal().getName();
        String newEmail = requestMessage.getData();
        if(oldEmail.equals(newEmail)){

        }
    }

    private void checkPhoneBusy(final WebSocketSession session,final RequestMessage requestMessage){

    }
}
