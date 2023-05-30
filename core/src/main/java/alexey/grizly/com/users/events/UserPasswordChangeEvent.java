package alexey.grizly.com.users.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserPasswordChangeEvent extends ApplicationEvent {
    private final EventParam param;

    public UserPasswordChangeEvent(EventParam param) {
        super(param);
        this.param = param;
    }

    @Getter
    @AllArgsConstructor
    public static class EventParam{
        private String email;
        private String changePasswordUrl;
    }

}
