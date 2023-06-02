package alexey.grizly.com.users.events;

import alexey.grizly.com.users.models.user.UserAccountWithEmailApprovedToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserEmailApprovedTokenEvent extends ApplicationEvent {
    private final UserAccountWithEmailApprovedToken userAccount;
    public UserEmailApprovedTokenEvent(UserAccountWithEmailApprovedToken userAccount) {
        super(userAccount);
        this.userAccount = userAccount;
    }
}
