package alexey.grizly.com.users.dtos.request;

import lombok.Data;

@Data
public class RestorePasswordRequestDto {
    private String email;
    private String password;
    private String token;
}
