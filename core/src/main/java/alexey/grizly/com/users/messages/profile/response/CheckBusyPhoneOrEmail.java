package alexey.grizly.com.users.messages.profile.response;

import lombok.Data;

@Data
public class CheckBusyPhoneOrEmail {
    private Boolean isBusy;
    private String param;
}
