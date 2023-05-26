package alexey.grizly.com.commons.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserPasswordChangeEvent extends ApplicationEvent {
    private EventParam param;

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
