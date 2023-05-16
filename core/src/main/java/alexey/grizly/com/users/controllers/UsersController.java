package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.commons.events.UserPasswordRestoreSendEmailEvent;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.dtos.request.ChangePasswordRequestDto;
import alexey.grizly.com.users.dtos.request.UserRegistrationRequestDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.UserAccountService;
import alexey.grizly.com.users.validators.PasswordValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    private final UserAccountService userAccountService;
    private final ApplicationEventMulticaster multicaster;
    private final GlobalProperties globalProperties;
    private final SecurityProperties securityProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UsersController(final UserAccountService pUserAccountService,
                                     final ApplicationEventMulticaster pMulticaster,
                                     final GlobalProperties globalProperties,
                                     final SecurityProperties properties,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountService = pUserAccountService;
        this.multicaster = pMulticaster;
        this.globalProperties = globalProperties;
        securityProperties = properties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping("password/change/token/{email}")
    public ResponseEntity<?> sendTokenForResetPassword(@PathVariable @Email final String email){
        UserAccount userAccount = userAccountService.getSimpleUserAccount(email);
        if(userAccount==null){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.NOT_FOUND,"Аккаунт с email: "+email+" не существует");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        }
        LocalDateTime expireTokenTime = LocalDateTime.now().plus(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLifetime(),
                securityProperties.getRestorePasswordTokenProperty().getUnit());
        Boolean result = userAccountService.generateAndSaveRestorePasswordToken(userAccount.getId(),expireTokenTime);
        if(result){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.CONFLICT,"Упс что-то пошло не так...");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
        }
        String url =globalProperties.getHost() + "/reset?token=" + Arrays.toString(Base64.getEncoder().encode((userAccount.getEmail() + "&&" + token).getBytes()));
        UserPasswordRestoreSendEmailEvent event = new UserPasswordRestoreSendEmailEvent(new UserPasswordRestoreSendEmailEvent.EventParam(userAccount.getEmail(),url));
        multicaster.multicastEvent(event);
        return ResponseEntity.ok("Инструкции по восстановлению пароля отправлены на email: "+userAccount.getEmail());
    }

    @PutMapping ("password/change")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePasswordRequestDto dto){
        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator(securityProperties.getUserPasswordStrange());
        if(!emailValidator.isValid(dto.getEmail(),null)
                || !passwordValidator.isValid(dto.getPassword(), null)
                || (dto.getToken()==null||dto.getToken().trim().length()!=36)
                ){
            String errorMessage = "Невалидные учетные данные пользователя";
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        String passwordHash = bCryptPasswordEncoder.encode(dto.getPassword());
        if(userAccountService.updatePassword(dto.getEmail(), passwordHash,dto.getToken())){
            return ResponseEntity.ok("Пароль изменен, перейдите к авторизации");
        }
        String errorMessage = "Неверные учетные данные пользователя";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationRequestDto dto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserRegistrationRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(6);
            for (ConstraintViolation<UserRegistrationRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }

    }
}
