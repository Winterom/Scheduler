package alexey.grizly.com.users.events;

import alexey.grizly.com.users.models.user.UserAccount;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    @Getter
    private final UserAccount userAccount;
    public UserRegistrationEvent(UserAccount userAccount) {
        super(userAccount);
        this.userAccount = userAccount;
    }

}
