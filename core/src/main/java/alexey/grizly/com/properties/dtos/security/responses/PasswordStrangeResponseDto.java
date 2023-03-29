package alexey.grizly.com.properties.dtos.security.responses;

import alexey.grizly.com.properties.models.SecurityProperties;
import lombok.Data;

@Data
public class PasswordStrangeResponseDto {
    private Integer minLowerCase;/*Минимальное количество прописных символов*/
    private Integer minNumber;/*Минимальное количество цифр*/
    private Integer minSymbol;/*Минимальное количество спец символов*/
    private Integer minUpperCase;/*Минимальное количество заглавных символов*/
    private Integer minCharacters;/*Минимальная длина пароля*/

    public PasswordStrangeResponseDto(SecurityProperties.UserPasswordStrange model){
        this.minLowerCase = model.getPasswordMinLowerCase();
        this.minNumber = model.getPasswordMinNumber();
        this.minSymbol = model.getPasswordMinSymbol();
        this.minUpperCase = model.getPasswordMinUpperCase();
        this.minCharacters = model.getPasswordMinCharacters();
    }
}
