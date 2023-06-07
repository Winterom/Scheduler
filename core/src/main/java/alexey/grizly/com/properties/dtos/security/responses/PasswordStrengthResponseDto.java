package alexey.grizly.com.properties.dtos.security.responses;

import alexey.grizly.com.properties.properties.SecurityProperties;
import lombok.Data;

import java.time.temporal.ChronoUnit;

@Data
public class PasswordStrengthResponseDto {
    private Integer minLowerCase;/*Минимальное количество прописных символов*/
    private Integer minNumber;/*Минимальное количество цифр*/
    private Integer minSymbol;/*Минимальное количество спец символов*/
    private Integer minUpperCase;/*Минимальное количество заглавных символов*/
    private Integer minCharacters;/*Минимальная длина пароля*/
    private Long passwordExpired;
    private ChronoUnit unit;

    public PasswordStrengthResponseDto(SecurityProperties.UserPasswordStrength model){
        this.minLowerCase = model.getPasswordMinLowerCase();
        this.minNumber = model.getPasswordMinNumber();
        this.minSymbol = model.getPasswordMinSymbol();
        this.minUpperCase = model.getPasswordMinUpperCase();
        this.minCharacters = model.getPasswordMinCharacters();
        this.passwordExpired = model.getPasswordExpired();
        this.unit = model.getUnit();
    }
}
