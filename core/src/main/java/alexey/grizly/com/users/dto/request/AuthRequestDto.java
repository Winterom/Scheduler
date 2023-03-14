package alexey.grizly.com.users.dto.request;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String email;
    private String phone;
    private String password;
}
