package alexey.grizly.com.users.dtos.request;

import alexey.grizly.com.users.validators.Password;
import alexey.grizly.com.users.validators.TokenLength;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    @Email(message = "Не валидный email")
    @Size(min = 5,max = 50, message = "Не валидный email")
    private String email;
    @Size(min = 5,max = 50,message = "Пароль не соответствует требованиям")
    @Password(message = "Пароль не соответствует требованиям")
    private String password;
    @TokenLength(type = TokenLength.ValidatorType.PASSWORD_TOKEN)
    private String token;
}
