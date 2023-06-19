package alexey.grizly.com.users.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserAccountChangeEvent extends ApplicationEvent {
    private final String email;
    public UserAccountChangeEvent(String email) {
        super(email);
        this.email = email;
    }
}
