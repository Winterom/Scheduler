package alexey.grizly.com.users.dtos.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String email;
    private String password;
    private String token;
}
