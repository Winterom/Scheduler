package alexey.grizly.com.users.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Запрос авторизации
 * @param authToken  токен идентификатора пользователя (телефон или email)
 * @param password пароль
 * */
@Data
public class AuthRequestDto {
    @Size(min = 4,max = 50)
    private String authToken;
    @NotEmpty
    @Size(min = 4,max = 100)
    private String password;
}
