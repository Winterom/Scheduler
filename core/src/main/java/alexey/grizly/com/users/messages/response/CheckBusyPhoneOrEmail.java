package alexey.grizly.com.users.messages.response;

import lombok.Data;

@Data
public class CheckBusyPhoneOrEmail {
    private Boolean isBusy;
    private String param;
}
