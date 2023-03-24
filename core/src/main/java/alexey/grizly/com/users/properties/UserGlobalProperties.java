package alexey.grizly.com.users.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserGlobalProperties {
    private JwtProperties jwtProperties = new JwtProperties();
    private UserPasswordStrange userPasswordStrange = new UserPasswordStrange();

    @Data
    public static class JwtProperties {
        private final String secret = "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g";
        private final Integer jwtLifetime = 3_600_000;
        private final Integer jwtRefreshLifetime = 36_000_000;
        private final Integer emailVerifyTokenLifeTime = 36_000_000;
    }

    @Data
    public static class UserPasswordStrange{
        private final Integer passwordMinLowerCase= 1;/*Минимальное количество прописных символов*/
        private final Integer passwordMinNumber=1;/*Минимальное количество цифр*/
        private final Integer passwordMinSymbol=2;/*Минимальное количество спец символов*/
        private final Integer passwordMinUpperCase= 1;/*Минимальное количество заглавных символов*/
        private final Integer passwordMinCharacters= 8;/*Минимальная длина пароля*/
    }
}
