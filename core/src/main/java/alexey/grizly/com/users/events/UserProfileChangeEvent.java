package alexey.grizly.com.users.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserProfileChangeEvent extends ApplicationEvent {
    private final String email;
    public UserProfileChangeEvent(String email) {
        super(email);
        this.email = email;
    }
}
