package alexey.grizly.com.commons.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserChangePasswordSendEmailEvent extends ApplicationEvent {
    public UserChangePasswordSendEmailEvent(Object source) {
        super(source);
    }

}
