package alexey.grizly.com.users.ws_handlers;

import alexey.grizly.com.commons.configs.ConnectionList;
import alexey.grizly.com.users.messages.RequestMessage;
import alexey.grizly.com.users.messages.roles.RolesRequestMessage;
import alexey.grizly.com.users.messages.roles.request.AllRolesAndAuthoritiesMessage;
import alexey.grizly.com.users.services.RolesAndAuthoritiesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
@Component
public class RolesAndAuthoritiesWebsocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ConnectionList connection;
    private final RolesAndAuthoritiesService rolesAndAuthoritiesService;

    public RolesAndAuthoritiesWebsocketHandler(ObjectMapper objectMapper, ConnectionList connection, RolesAndAuthoritiesService rolesAndAuthoritiesService) {
        this.objectMapper = objectMapper;
        this.connection = connection;
        this.rolesAndAuthoritiesService = rolesAndAuthoritiesService;
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
        RequestMessage requestMessage = objectMapper.readValue(request, RolesRequestMessage.class);
        switch (requestMessage.getEvent()){
            case "ALL_ROLES_WITH_AUTHORITIES"-> getAllRolesWithAuthoritiesPage(session,requestMessage);
            default -> unknownEvent(session);
        }
    }

    public void unknownEvent(final WebSocketSession session) throws IOException {
        session.close();
    }

    public void getAllRolesWithAuthoritiesPage(WebSocketSession session,RequestMessage requestMessage) throws JsonProcessingException {
        AllRolesAndAuthoritiesMessage request = objectMapper.readValue(requestMessage.getData(), AllRolesAndAuthoritiesMessage.class);

    }


}
