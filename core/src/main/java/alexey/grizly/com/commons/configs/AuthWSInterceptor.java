package alexey.grizly.com.commons.configs;

import alexey.grizly.com.users.utils.JwtTokenUtil;
import io.jsonwebtoken.ClaimJwtException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class AuthWSInterceptor implements ChannelInterceptor {
    private final JwtTokenUtil jwtTokenUtil;
    public AuthWSInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil= jwtTokenUtil;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        List<String> listHeader = accessor.getNativeHeader("Authorization");
        String authHeader =null;
        if(listHeader!=null&&!listHeader.isEmpty()){
            authHeader = listHeader.get(0);
        }
        String authToken = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                authToken = jwtTokenUtil.getEmailFromToken(jwt);
            }catch (ClaimJwtException e){
               return message;
            }

        }
        if (authToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authToken, accessor.getHeader("simpSessionId"),
                    jwtTokenUtil.getAuthorities(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);

            accessor.setUser(token);

        }
        return message;
    }

}
