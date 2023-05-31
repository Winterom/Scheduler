package alexey.grizly.com.users.validators;

import alexey.grizly.com.properties.properties.SecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenLengthValidator implements ConstraintValidator<TokenLength, String> {
    private TokenLength.ValidatorType validatorType;
    private final SecurityProperties securityProperties;

    public TokenLengthValidator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void initialize(TokenLength constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.validatorType = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null||value.isEmpty()){
            return false;
        }
        if(this.validatorType.equals(TokenLength.ValidatorType.EMAIL_TOKEN)){
            return value.length()== securityProperties.getApprovedEmailProperty().getApprovedEmailTokenLength();
        }
        if(this.validatorType.equals(TokenLength.ValidatorType.PASSWORD_TOKEN)){
            return value.length()== securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLength();
        }
        return false;
    }
}
