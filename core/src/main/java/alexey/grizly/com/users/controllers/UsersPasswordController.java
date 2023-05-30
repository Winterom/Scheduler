package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.request.ChangePasswordRequestDto;
import alexey.grizly.com.users.services.UserPasswordService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/v1/users/password")
public class UsersPasswordController {
    private final UserPasswordService userPasswordService;

    private final Validator validator;

    @Autowired
    public UsersPasswordController(final UserPasswordService pUserPasswordService,
                                   final Validator validator) {
        this.userPasswordService = pUserPasswordService;
        this.validator = validator;
    }


    @GetMapping("change/{email}")
    public ResponseEntity<?> requestForChangePassword(@PathVariable final String email){
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.isValid(email,null)){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Аккаунт "+ email + " не существует");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        List<String> errorMessages = userPasswordService.createChangePasswordToken(email);
        if(!errorMessages.isEmpty()){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,errorMessages);
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(null);
    }

    @PutMapping ("change")
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
        this.userPasswordService.changePassword(dto.getEmail(), dto.getPassword(), dto.getToken());
        return ResponseEntity.ok(null);
    }

}
