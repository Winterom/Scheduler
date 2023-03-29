package alexey.grizly.com.users.validators;

import alexey.grizly.com.properties.models.SecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator  implements ConstraintValidator<Password, String> {
    private  final SecurityProperties.UserPasswordStrange userPasswordStrange;

    public PasswordValidator(SecurityProperties.UserPasswordStrange userPasswordStrange) {
        this.userPasswordStrange = userPasswordStrange;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean validMinCharacter = true;
        boolean validMinNumber = true;
        boolean validMinSymbol = true;
        boolean validMinLowCase = true;
        boolean validMinUpperCase = true;
        if(value==null||value.trim().isEmpty()||value.length()>=50){
            return false;
        }
        if(userPasswordStrange.getPasswordMinCharacters()!=null&&
            userPasswordStrange.getPasswordMinCharacters()!=0){
            if (value.length()<userPasswordStrange.getPasswordMinCharacters()){
                validMinCharacter=false;
            }
        }
        if(userPasswordStrange.getPasswordMinNumber()!=null&&
        userPasswordStrange.getPasswordMinNumber()!=0){
            Pattern pattern = Pattern.compile("([^0-9]*[0-9])");
            Matcher matcher = pattern.matcher(value);
            int matches=0;
            while (matcher.find()){
                matches++;
            }
            if (matches<userPasswordStrange.getPasswordMinNumber()){
                validMinNumber=false;
            }
        }
        if(userPasswordStrange.getPasswordMinSymbol()!=null&&
          userPasswordStrange.getPasswordMinSymbol()!=0){
            Pattern pattern = Pattern.compile("([$@!%*?&])");
            Matcher matcher = pattern.matcher(value);
            int matches=0;
            while (matcher.find()){
                matches++;
            }
            if (matches<userPasswordStrange.getPasswordMinSymbol()){
                validMinSymbol=false;
            }
        }
        if(userPasswordStrange.getPasswordMinLowerCase()!=null&&
                userPasswordStrange.getPasswordMinLowerCase()!=0){
            Pattern pattern = Pattern.compile("([^a-z]*[a-z])");
            Matcher matcher = pattern.matcher(value);
            int matches=0;
            while (matcher.find()){
                matches++;
            }
            if (matches<userPasswordStrange.getPasswordMinLowerCase()){
                validMinLowCase=false;
            }
        }
        if(userPasswordStrange.getPasswordMinUpperCase()!=null&&
                userPasswordStrange.getPasswordMinUpperCase()!=0){
            Pattern pattern = Pattern.compile("([^A-Z]*[A-Z])");
            Matcher matcher = pattern.matcher(value);
            int matches=0;
            while (matcher.find()){
                matches++;
            }
            if (matches<userPasswordStrange.getPasswordMinUpperCase()){
                validMinUpperCase=false;
            }
        }
        return validMinCharacter
                && validMinNumber
                && validMinSymbol
                && validMinLowCase
                && validMinUpperCase;
    }

}
