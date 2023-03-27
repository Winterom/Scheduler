package alexey.grizly.com.properties.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SecurityProperties {
    private JwtProperties jwtProperties = new JwtProperties();
    private UserPasswordStrange userPasswordStrange = new UserPasswordStrange();

    @Getter
    @Setter
    public static class JwtProperties {
        private final String secret = "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g";
        private final Integer jwtLifetime = 3_600_000;
        private final Integer jwtRefreshLifetime = 36_000_000;
        private final Integer emailVerifyTokenLifeTime = 36_000_000;

        @Override
        public String toString() {
            return "JwtProperties{" +
                    "secret='" + secret + '\'' +
                    ", jwtLifetime=" + jwtLifetime +
                    ", jwtRefreshLifetime=" + jwtRefreshLifetime +
                    ", emailVerifyTokenLifeTime=" + emailVerifyTokenLifeTime +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class UserPasswordStrange{
        private final Integer passwordMinLowerCase= 1;/*Минимальное количество прописных символов*/
        private final Integer passwordMinNumber=1;/*Минимальное количество цифр*/
        private final Integer passwordMinSymbol=2;/*Минимальное количество спец символов*/
        private final Integer passwordMinUpperCase= 1;/*Минимальное количество заглавных символов*/
        private final Integer passwordMinCharacters= 8;/*Минимальная длина пароля*/

        @Override
        public String toString() {
            return "UserPasswordStrange{" +
                    "passwordMinLowerCase=" + passwordMinLowerCase +
                    ", passwordMinNumber=" + passwordMinNumber +
                    ", passwordMinSymbol=" + passwordMinSymbol +
                    ", passwordMinUpperCase=" + passwordMinUpperCase +
                    ", passwordMinCharacters=" + passwordMinCharacters +
                    '}';
        }
    }

}
