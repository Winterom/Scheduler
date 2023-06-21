package alexey.grizly.com.commons.configs;

import alexey.grizly.com.users.ws_handlers.ProfileWebsocketHandler;
import alexey.grizly.com.users.ws_handlers.RolesAndAuthoritiesWebsocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private static final String BASE_URL = "api/v1/ws";
    private static final String ALLOWED_ORIGIN="http://localhost:4200/";
    private static final String USERS_PROFILE_URL="/users/profile";
    private static final String ROLES_AUTHORITIES_URL="/roles";
    private final ProfileWebsocketHandler profileWebsocketHandler;
    private final JwtHandshakeHandler jwtHandshakeHandler;
    private final RolesAndAuthoritiesWebsocketHandler rolesAndAuthoritiesWebsocketHandler;

    @Autowired
    public WebSocketConfiguration(ProfileWebsocketHandler profileWebsocketHandler, JwtHandshakeHandler jwtHandshakeHandler, RolesAndAuthoritiesWebsocketHandler rolesAndAuthoritiesWebsocketHandler) {
        this.profileWebsocketHandler = profileWebsocketHandler;
        this.jwtHandshakeHandler = jwtHandshakeHandler;
        this.rolesAndAuthoritiesWebsocketHandler = rolesAndAuthoritiesWebsocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(profileWebsocketHandler,BASE_URL+USERS_PROFILE_URL)
                .setAllowedOrigins(ALLOWED_ORIGIN).setHandshakeHandler(jwtHandshakeHandler);
        registry.addHandler(rolesAndAuthoritiesWebsocketHandler,BASE_URL+ROLES_AUTHORITIES_URL)
                .setAllowedOrigins(ALLOWED_ORIGIN).setHandshakeHandler(jwtHandshakeHandler);
    }



}
