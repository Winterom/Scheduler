package alexey.grizly.com.commons.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserPasswordRestoreSendEmailEvent extends ApplicationEvent {
    private EventParam param;

    public UserPasswordRestoreSendEmailEvent(EventParam param) {
        super(param);
        this.param = param;
    }

    @Getter
    @AllArgsConstructor
    public static class EventParam{
        private String email;
        private String restorePasswordUrl;
    }

}
