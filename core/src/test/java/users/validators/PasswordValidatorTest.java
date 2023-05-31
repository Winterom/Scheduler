package users.validators;

import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class PasswordValidatorTest {
    private static String password;
    @BeforeAll
    public static void initParam() {

    }

    @Test
    void validMinMaxLength() {
        /*только длина должна быть не больше 50 и не меньше числа указанного в UserPasswordStrange.passwordMinCharacters*/
        SecurityProperties.UserPasswordStrange userPasswordStrange = new SecurityProperties.UserPasswordStrange();
        userPasswordStrange.setPasswordMinCharacters(8);
        System.out.println(userPasswordStrange);
        password = generatingRandomStringBounded(8,65,122);
        PasswordValidator validator = new PasswordValidator(userPasswordStrange);
        System.out.println("Длина пароля: 8 пароль: "+password);
        Assertions.assertTrue(validator.isValid(password, null));
        String password2 = generatingRandomStringBounded(3,97,122);
        System.out.println("Длина пароля: 3 пароль: "+password2);
        Assertions.assertFalse(validator.isValid(password2, null));
        String password3 =generatingRandomStringBounded(51,97,122);
        System.out.println("Длина пароля: 51 пароль: "+password3);
        Assertions.assertFalse(validator.isValid(password3, null));
    }
    @Test
    void validNumberCount(){
        SecurityProperties.UserPasswordStrange userPasswordStrange = new SecurityProperties.UserPasswordStrange();
        userPasswordStrange.setPasswordMinNumber(4);
        System.out.println(userPasswordStrange);
        PasswordValidator validator = new PasswordValidator(userPasswordStrange);
        String badPassword = password+generatingRandomStringBounded(3,48,57);
        System.out.println("Количество цифр 3 Длина пароля:"+badPassword.length()+ " пароль: "+badPassword);
        Assertions.assertFalse(validator.isValid(badPassword,null));
        password =password + generatingRandomStringBounded(4,48,57);
        System.out.println("Количество цифр 4 Длина пароля:"+password.length()+ " пароль: "+password);
        Assertions.assertTrue(validator.isValid(password, null));


    }
    @Test
    void validUpperCaseCount(){
        SecurityProperties.UserPasswordStrange userPasswordStrange = new SecurityProperties.UserPasswordStrange();
        userPasswordStrange.setPasswordMinUpperCase(4);
        System.out.println(userPasswordStrange);
        password =password+ generatingRandomStringBounded(4,65,90);
        PasswordValidator validator = new PasswordValidator(userPasswordStrange);
        System.out.println("Длина пароля: 4 пароль: "+password);
        Assertions.assertTrue(validator.isValid(password, null));
        password = generatingRandomStringBounded(10,97,122);
        System.out.println("Длина пароля: 10 пароль прописных нет: "+password);
        Assertions.assertFalse(validator.isValid(password,null));
        password = generatingRandomStringBounded(3,65,90);
        System.out.println("Длина пароля: 3 пароль : "+password);
        Assertions.assertFalse(validator.isValid(password,null));
    }
    @Test
    void validLowCaseCount(){
        SecurityProperties.UserPasswordStrange userPasswordStrange = new SecurityProperties.UserPasswordStrange();
        userPasswordStrange.setPasswordMinLowerCase(4);
        System.out.println(userPasswordStrange);
        password = generatingRandomStringBounded(4,97,122);
        PasswordValidator validator = new PasswordValidator(userPasswordStrange);
        System.out.println("Длина пароля: 4 пароль: "+password);
        Assertions.assertTrue(validator.isValid(password, null));
        password = generatingRandomStringBounded(4,65,90);
        System.out.println("Длина пароля: 4 пароль : "+password);
        Assertions.assertFalse(validator.isValid(password,null));
        password = generatingRandomStringBounded(3,97,122);
        System.out.println("Длина пароля: 3 пароль : "+password);
        Assertions.assertFalse(validator.isValid(password,null));
    }
    @Test
    void validSpecSymbolCount(){
        SecurityProperties.UserPasswordStrange userPasswordStrange = new SecurityProperties.UserPasswordStrange();
        userPasswordStrange.setPasswordMinSymbol(3);
        System.out.println(userPasswordStrange);
        password ="afgfwtt$@!";
        PasswordValidator validator = new PasswordValidator(userPasswordStrange);
        System.out.println("Пароль: "+password);
        Assertions.assertTrue(validator.isValid(password, null));
        password = "afgfwtt@!";
        System.out.println("Пароль : "+password);
        Assertions.assertFalse(validator.isValid(password,null));
        password = "afgfwtt";
        System.out.println("Длина пароля: 3 пароль : "+password);
        Assertions.assertFalse(validator.isValid(password,null));
    }

    public String generatingRandomStringBounded(int targetStringLength,int leftLimit,int rightLimit) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
       return buffer.toString();
    }


}