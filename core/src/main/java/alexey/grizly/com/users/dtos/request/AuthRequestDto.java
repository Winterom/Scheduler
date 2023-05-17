package alexey.grizly.com.users.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {
    @NotEmpty
    @Size(min = 4,max = 50)
    private String emailOrPhone;
    @NotEmpty
    @Size(min = 4,max = 50)
    private String password;
}
