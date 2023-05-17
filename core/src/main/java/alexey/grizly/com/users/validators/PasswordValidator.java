package alexey.grizly.com.users.validators;

import alexey.grizly.com.properties.properties.SecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.*;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class PasswordValidator  implements ConstraintValidator<Password, String> {

    private SecurityProperties.UserPasswordStrange userPasswordStrange;


    public PasswordValidator (SecurityProperties.UserPasswordStrange userPasswordStrange){
        this.userPasswordStrange = userPasswordStrange;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null||value.trim().isEmpty()||value.length()>=50){
            return false;
        }
        if(userPasswordStrange.getPasswordMinCharacters()!=null&&
                userPasswordStrange.getPasswordMinCharacters()!=0){
            if (value.length()< userPasswordStrange.getPasswordMinCharacters()){
               return false;
            }
        }
        StringBuilder sb = new StringBuilder();
        if(userPasswordStrange.getPasswordMinNumber()!=null&&
                userPasswordStrange.getPasswordMinNumber()!=0){
            sb.append("([^0-9]*[0-9])")
                    .append("{")
                    .append(userPasswordStrange.getPasswordMinNumber())
                    .append("}");
        }
        if(userPasswordStrange.getPasswordMinSymbol()!=null&&
                userPasswordStrange.getPasswordMinSymbol()!=0){
            sb.append("([$@!%*?&])")
                    .append("{")
                    .append(userPasswordStrange.getPasswordMinSymbol())
                    .append("}");
        }
        if(userPasswordStrange.getPasswordMinLowerCase()!=null&&
                userPasswordStrange.getPasswordMinLowerCase()!=0){
            sb.append("([^a-z]*[a-z])")
                    .append("{")
                    .append(userPasswordStrange.getPasswordMinLowerCase())
                    .append("}");
        }
        if(userPasswordStrange.getPasswordMinUpperCase()!=null&&
                userPasswordStrange.getPasswordMinUpperCase()!=0){
            sb.append("([^A-Z]*[A-Z])")
                    .append("{")
                    .append(userPasswordStrange.getPasswordMinUpperCase())
                    .append("}");
        }
        if(sb.isEmpty()){
            return true;
        }
        Pattern pattern = Pattern.compile(sb.toString());
        Matcher matcher = pattern.matcher(value);
        return (matcher.find());
    }

}
