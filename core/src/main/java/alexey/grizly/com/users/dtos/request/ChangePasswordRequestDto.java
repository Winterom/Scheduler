package alexey.grizly.com.users.dtos.request;

import alexey.grizly.com.users.validators.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Поле токен не может быть пустым")
    @Size(max = 100,message = "Поле токен не соответствует требования")
    private String token;
}
