package alexey.grizly.com.users.dtos.request;

import alexey.grizly.com.users.validators.TokenLength;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ApprovedEmailRequestDto {
    @Email(message = "Не валидный email")
    @Size(min = 5,max = 50, message = "Не валидный email")
    private String email;
    @TokenLength(type = TokenLength.ValidatorType.EMAIL_TOKEN)
    private String token;
}
