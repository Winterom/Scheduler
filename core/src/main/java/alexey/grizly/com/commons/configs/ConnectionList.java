package alexey.grizly.com.commons.configs;

import lombok.Data;
import org.jetbrains.annotations.Nullable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Data
@Component
public class ConnectionList {
    private final Map<String, ConcurrentWebSocketSessionDecorator> clients = new ConcurrentHashMap<>();
    @Nullable
    public WebSocketSession getSession(@NonNull String email){
        WebSocketSession session = this.clients.get(email);
        if(session==null){
            return null;
        }
        if(!session.isOpen()){
            this.clients.remove(email);
            return null;
        }
        return session;
    }

    public void putSession(@NonNull String email,@NonNull WebSocketSession session){
        this.clients.put(email,new ConcurrentWebSocketSessionDecorator(session,2000, 4096));
    }

    public void deleteSession(@NonNull String email){
        this.clients.remove(email);
    }
}
