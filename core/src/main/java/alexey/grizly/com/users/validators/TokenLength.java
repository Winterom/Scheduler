package alexey.grizly.com.users.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TokenLengthValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenLength {
    String message() default "Невалидный токен подтверждения";
    ValidatorType type() default ValidatorType.EMAIL_TOKEN;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public enum ValidatorType{
        EMAIL_TOKEN,
        PASSWORD_TOKEN
    }
}
