package alexey.grizly.com.commons.events;

import alexey.grizly.com.users.models.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class UserRegistrationEvent extends ApplicationEvent {
    private final EventParam eventParam;
    public UserRegistrationEvent(EventParam eventParam) {
        super(eventParam);
        this.eventParam = eventParam;
    }

    @Getter
    @AllArgsConstructor
    public static class EventParam{
        private UserAccount userAccount;
        private String url;
    }
}
