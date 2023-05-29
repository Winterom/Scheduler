package alexey.grizly.com.commons.events;

import alexey.grizly.com.users.models.UserAccount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    @Getter
    private final UserAccount userAccount;
    @Getter
    @Setter
    private String token;
    public UserRegistrationEvent(UserAccount userAccount) {
        super(userAccount);
        this.userAccount = userAccount;
    }

}
