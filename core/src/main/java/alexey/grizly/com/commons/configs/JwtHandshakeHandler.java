package alexey.grizly.com.commons.configs;

import alexey.grizly.com.users.utils.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;



@Component
public class JwtHandshakeHandler extends DefaultHandshakeHandler {
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtHandshakeHandler(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest req, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ServletServerHttpRequest request = (ServletServerHttpRequest) req;
        String query = request.getURI().getQuery();
        if(query==null||!query.startsWith("bearer=")){
            return super.determineUser(req, wsHandler, attributes);
        }
        String jwt = query.substring(7);
        if(jwt.isEmpty()){
            return super.determineUser(req, wsHandler, attributes);
        }
        String authToken;
        try {
            authToken = jwtTokenUtil.getEmailFromToken(jwt);
        }catch (JwtException e){
            return super.determineUser(req, wsHandler, attributes);
        }
        return new UsernamePasswordAuthenticationToken(authToken, request.getRemoteAddress(),
                    jwtTokenUtil.getAuthorities(jwt).stream().map(SimpleGrantedAuthority::new).toList());
        }

}
