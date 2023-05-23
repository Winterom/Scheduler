package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.commons.events.UserPasswordRestoreSendEmailEvent;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.dtos.request.ChangePasswordRequestDto;
import alexey.grizly.com.users.dtos.request.UserRegistrationRequestDto;
import alexey.grizly.com.users.dtos.response.UserResponseDto;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.UserAccountService;
import alexey.grizly.com.users.validators.PhoneNumberValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    private final UserAccountService userAccountService;
    private final ApplicationEventMulticaster multicaster;
    private final GlobalProperties globalProperties;
    private final SecurityProperties securityProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Validator validator;

    @Autowired
    public UsersController(final UserAccountService pUserAccountService,
                           final ApplicationEventMulticaster pMulticaster,
                           final GlobalProperties globalProperties,
                           final SecurityProperties properties,
                           final BCryptPasswordEncoder bCryptPasswordEncoder, Validator validator) {
        this.userAccountService = pUserAccountService;
        this.multicaster = pMulticaster;
        this.globalProperties = globalProperties;
        securityProperties = properties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
    }


    @GetMapping("password/change/{email}")
    public ResponseEntity<?> sendTokenForResetPassword(@PathVariable @Email final String email){
        UserAccount userAccount = userAccountService.getSimpleUserAccount(email);
        if(userAccount==null){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Аккаунт с email: "+email+" не существует");
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        }
        LocalDateTime expireTokenTime = LocalDateTime.now().plus(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLifetime(),
                securityProperties.getRestorePasswordTokenProperty().getUnit());
        String token = generateRestorePasswordToken();
        userAccountService.saveRestorePasswordToken(userAccount.getId(),expireTokenTime,token);
        String url =globalProperties.getHost() + "/reset?token=" + Arrays.toString(Base64.getEncoder().encode((userAccount.getEmail() + "&&" + token).getBytes()));
        UserPasswordRestoreSendEmailEvent event = new UserPasswordRestoreSendEmailEvent(new UserPasswordRestoreSendEmailEvent.EventParam(userAccount.getEmail(),url));
        multicaster.multicastEvent(event);
        return ResponseEntity.ok(new UserResponseDto(userAccount.getEmail()));
    }

    @PutMapping ("password/change")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePasswordRequestDto dto){
        Set<ConstraintViolation<ChangePasswordRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(6);
            for (ConstraintViolation<ChangePasswordRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }
        String passwordHash = bCryptPasswordEncoder.encode(dto.getPassword());
        if(userAccountService.updatePassword(dto.getEmail(), passwordHash,dto.getToken())){
            return ResponseEntity.ok(new UserResponseDto("Пароль изменен, перейдите к авторизации"));
        }
        String errorMessage = "Неверные учетные данные пользователя";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody final UserRegistrationRequestDto dto){
        Set<ConstraintViolation<UserRegistrationRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(6);
            for (ConstraintViolation<UserRegistrationRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }
        String passwordHash = bCryptPasswordEncoder.encode(dto.getPassword());
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime credentialExpired = createdAt.plus(securityProperties.getPasswordProperty().getPasswordExpired(),
                securityProperties.getPasswordProperty().getUnit());
        Long userId = userAccountService.createNewUserAccount(dto.getEmail(),
                                            dto.getPhone(),
                                            passwordHash,
                                            credentialExpired,
                                            EUserStatus.NEW_USER,
                                            createdAt);
        if(userId==null){
            return new ResponseEntity<>("Не удалось создать аккаунт",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new UserResponseDto("Аккаунт успешно создан"));
    }

    @GetMapping("check-email/{email}")
    public ResponseEntity<?> emailBusyCheck(@PathVariable String email){
        EmailValidator validator = new EmailValidator();
        if(!validator.isValid(email,null)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Невалидные параметры запроса");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        if(userAccountService.emailBusyCheck(email)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.CONFLICT,"email "+ email +" занят другим аккаунтом");
            return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("check-phone/{phone}")
    public ResponseEntity<?> phoneBusyCheck(@PathVariable String phone){
        PhoneNumberValidator validator = new PhoneNumberValidator();
        if(!validator.isValid(phone,null)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Невалидные параметры запроса");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        if(userAccountService.phoneBusyCheck(phone)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.CONFLICT,"Телефон "+ phone +" занят другим аккаунтом");
            return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private String generateRestorePasswordToken(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = this.securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLength();
        Random random = new SecureRandom();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }

}
