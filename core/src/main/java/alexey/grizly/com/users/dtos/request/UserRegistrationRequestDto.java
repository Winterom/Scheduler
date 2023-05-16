package alexey.grizly.com.users.dtos.request;

import alexey.grizly.com.users.validators.Password;
import alexey.grizly.com.users.validators.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @Email(message = "Не валидный email")
    @Size(min = 5, max = 50, message = "Не валидный email")
    private String email;
    @Size(min = 5, max = 50,message = "Не валидный номер телефона")
    @PhoneNumber(message = "Не валидный номер телефона")
    private String phone;
    @Size(min = 5,max = 50,message = "Пароль не соответствует требований")
    @Password(message = "Пароль не соответствует требований")
    private String password;
    @Size(min = 1,max = 50,message = "Длина поля имя не ")
    private String name;
    @Size(min = 1,max = 50)
    private String surname;
    @Size(max = 50)
    private String lastname;
}
