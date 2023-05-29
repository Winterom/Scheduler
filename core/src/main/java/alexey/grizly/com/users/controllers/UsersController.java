package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.events.UserPasswordChangeEvent;
import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.dtos.request.ChangePasswordRequestDto;
import alexey.grizly.com.users.dtos.response.UserResponseDto;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.UserAccountService;
import alexey.grizly.com.users.utils.ApprovedTokenUtils;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    private final UserAccountService userAccountService;
    private final ApplicationEventMulticaster eventMulticaster;
    private final GlobalProperties globalProperties;
    private final SecurityProperties securityProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Validator validator;

    @Autowired
    public UsersController(final UserAccountService pUserAccountService,
                           final ApplicationEventMulticaster multicaster,
                           final GlobalProperties globalProperties,
                           final SecurityProperties properties,
                           final BCryptPasswordEncoder bCryptPasswordEncoder, Validator validator) {
        this.userAccountService = pUserAccountService;
        this.eventMulticaster = multicaster;
        this.globalProperties = globalProperties;
        securityProperties = properties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
    }


    @GetMapping("password/change/{email}")
    public ResponseEntity<?> requestForChangePassword(@PathVariable @Email final String email){
        UserAccount userAccount = userAccountService.getSimpleUserAccount(email);
        if(userAccount==null){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Аккаунт с email: "+email+" не существует");
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        }
        LocalDateTime expireTokenTime = LocalDateTime.now().plus(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLifetime(),
                securityProperties.getRestorePasswordTokenProperty().getUnit());
        String token = ApprovedTokenUtils.generateApprovedToken(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLength());
        userAccountService.saveChangePasswordToken(userAccount.getId(),expireTokenTime,token);
        String url =globalProperties.getHost() + "password/restore?email=" +userAccount.getEmail()+"&token="+ token;
        UserPasswordChangeEvent event = new UserPasswordChangeEvent(new UserPasswordChangeEvent.EventParam(userAccount.getEmail(),url));
        eventMulticaster.multicastEvent(event);
        return ResponseEntity.ok(new UserResponseDto(userAccount.getEmail()));
    }

    @PutMapping ("password/change")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePasswordRequestDto dto){
        Set<ConstraintViolation<ChangePasswordRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(6);
            for (ConstraintViolation<ChangePasswordRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
            }
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        UserAccount userAccount = userAccountService.checkPasswordChangeToken(dto.getEmail(),dto.getToken());
        if(userAccount==null){
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,"Неверные учетные данные пользователя");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
        }
        if(bCryptPasswordEncoder.matches(dto.getPassword(), userAccount.getPassword())){
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,"Новый пароль не должен совпадать со старым");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
        }
        String passwordHash = bCryptPasswordEncoder.encode(dto.getPassword());
        LocalDateTime credentialExpired = LocalDateTime.now().plus(securityProperties.getPasswordProperty().getPasswordExpired(),
                securityProperties.getPasswordProperty().getUnit());
        userAccountService.updatePassword(userAccount, passwordHash,credentialExpired);
        userAccountService.deleteUsedChangePasswordTokenByUserId(userAccount.getId());
        return ResponseEntity.ok(new UserResponseDto("Пароль изменен, перейдите к авторизации"));
    }

    @GetMapping("check-email/{email}")
    public ResponseEntity<?> emailBusyCheck(@PathVariable String email){
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.isValid(email,null)){
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
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        if(!phoneNumberValidator.isValid(phone,null)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Неверный формат телефона");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        if(userAccountService.phoneBusyCheck(phone)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.CONFLICT,"Телефон "+ phone +" занят другим аккаунтом");
            return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
