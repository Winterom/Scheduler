package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.services.UserPhoneNumberService;
import alexey.grizly.com.users.validators.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/phone")
public class UserPhoneNumberController {

    private final UserPhoneNumberService userPhoneNumberService;

    @Autowired
    public UserPhoneNumberController(UserPhoneNumberService userPhoneNumberService) {
        this.userPhoneNumberService = userPhoneNumberService;
    }

    @GetMapping("check/{phone}")
    public ResponseEntity<?> phoneBusyCheck(@PathVariable String phone){
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        if(!phoneNumberValidator.isValid(phone,null)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Неверный формат телефона");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        if(userPhoneNumberService.phoneBusyCheck(phone)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.CONFLICT,"Телефон "+ phone +" занят другим аккаунтом");
            return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
