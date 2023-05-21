package alexey.grizly.com.users.dtos.request;

import alexey.grizly.com.users.validators.Password;
import alexey.grizly.com.users.validators.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @Email(regexp=".*@.*\\..*",message = "Не валидный email")
    @Size(min = 5, max = 50, message = "Не валидный email")
    private String email;
    @Size(min = 5, max = 50,message = "Не валидный номер телефона")
    @PhoneNumber(message = "Не валидный номер телефона",required = false)
    private String phone;
    @Size(min = 5,max = 50,message = "Пароль не соответствует требований")
    @Password
    private String password;
    @NotBlank(message = "Поле Имя не должно быть пустым")
    @Size(min = 1,max = 50,message = "Длина поля Имя не соответствует требованиям")
    private String name;
    @NotBlank(message = "Поле Фамилия не должна быть пустым")
    @Size(min = 1,max = 50,message = "Длина поля Фамилия не соответствует требованиям")
    private String surname;
    @Size(max = 50,message = "Длина поля Отчество не соответствует требованиям")
    private String lastname;
}
