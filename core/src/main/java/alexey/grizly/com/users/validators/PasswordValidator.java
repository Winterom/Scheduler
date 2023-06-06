package alexey.grizly.com.users.validators;

import alexey.grizly.com.properties.properties.SecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator  implements ConstraintValidator<Password, String> {
    private  final SecurityProperties.UserPasswordStrength userPasswordStrength;

    public PasswordValidator(SecurityProperties.UserPasswordStrength userPasswordStrength) {
        this.userPasswordStrength = userPasswordStrength;
    }
    @Autowired
    public PasswordValidator(final SecurityProperties securityProperties){
        this.userPasswordStrength = securityProperties.getUserPasswordStrange();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null||value.trim().isEmpty()||value.length()>=50){
            return false;
        }
        if(userPasswordStrength.getPasswordMinCharacters()!=null&&
                userPasswordStrength.getPasswordMinCharacters()!=0
                &&(value.length()< userPasswordStrength.getPasswordMinCharacters())){
               return false;
            }

        StringBuilder sb = new StringBuilder();
        if(userPasswordStrength.getPasswordMinNumber()!=null&&
                userPasswordStrength.getPasswordMinNumber()!=0){
            sb.append("(?=([^0-9]*[0-9])")
                    .append("{")
                    .append(userPasswordStrength.getPasswordMinNumber())
                    .append(",})");
        }
        if(userPasswordStrength.getPasswordMinSymbol()!=null&&
                userPasswordStrength.getPasswordMinSymbol()!=0){
            sb.append("(?=(.*[$@$!%*?&])")
                    .append("{")
                    .append(userPasswordStrength.getPasswordMinSymbol())
                    .append(",})");
        }
        if(userPasswordStrength.getPasswordMinLowerCase()!=null&&
                userPasswordStrength.getPasswordMinLowerCase()!=0){
            sb.append("(?=([^a-z]*[a-z])")
                    .append("{")
                    .append(userPasswordStrength.getPasswordMinLowerCase())
                    .append(",})");
        }
        if(userPasswordStrength.getPasswordMinUpperCase()!=null&&
                userPasswordStrength.getPasswordMinUpperCase()!=0){
            sb.append("(?=([^A-Z]*[A-Z])")
                    .append("{")
                    .append(userPasswordStrength.getPasswordMinUpperCase())
                    .append(",})");
        }
        if(sb.isEmpty()){
            return true;
        }
        /*(?=([^a-z]*[a-z]){1,})(?=([^A-Z]*[A-Z]){1,})(?=([^0-9]*[0-9]){1,})*/
        Pattern pattern = Pattern.compile(sb.toString());
        Matcher matcher = pattern.matcher(value);
        return (matcher.find());
    }

}
