package alexey.grizly.com.users.controllers;


import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.commons.events.UserPasswordRestoreSendEmailEvent;
import alexey.grizly.com.properties.models.GlobalProperties;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.UserAccountService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/users")
public class UserAccountsController {
    private final UserAccountService userAccountService;
    private final ApplicationEventMulticaster multicaster;
    private final GlobalProperties globalProperties;

    @Autowired
    public UserAccountsController(UserAccountService userAccountService, ApplicationEventMulticaster multicaster, GlobalProperties globalProperties) {
        this.userAccountService = userAccountService;
        this.multicaster = multicaster;

        this.globalProperties = globalProperties;
    }


    @GetMapping("user/password/change/token/{email}")
    public ResponseEntity<?> sendTokenForResetPassword(@PathVariable @Email String email){
        UserAccount userAccount = userAccountService.getSimpleUserAccount(email);
        if(userAccount==null){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.NOT_FOUND,"Аккаунт с email: "+email+" не существует");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        }
        String token = userAccountService.generateAndSaveRestorePasswordToken(userAccount);
        if(token==null){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.CONFLICT,"Упс что-то пошло не так...");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
        }
        String url =globalProperties.getHost()+"/reset?token="+ Arrays.toString(Base64.getEncoder().encode((userAccount.getEmail() + "&&" + token).getBytes()));
        UserPasswordRestoreSendEmailEvent event = new UserPasswordRestoreSendEmailEvent(new UserPasswordRestoreSendEmailEvent.EventParam(userAccount.getEmail(),url));
        multicaster.multicastEvent(event);
        return ResponseEntity.ok("Инструкции по восстановлению пароля отправлены на email: "+userAccount.getEmail());
    }

    @PutMapping ("user/password/change")
    public ResponseEntity<?> changePassword(){
        return ResponseEntity.ok("Пароль изменен");
    }

}
