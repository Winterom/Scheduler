package alexey.grizly.com.users.messages.profile.request;

import lombok.Data;

@Data
public class UpdateProfileRequestMessage {
    private Long id;
    private String email;
    private String phone;
}
