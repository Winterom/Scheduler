package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.request.ApprovedEmailRequestDto;
import alexey.grizly.com.users.services.UserEmailService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users/email")
public class UserEmailController {
    private final Validator validator;
    private final UserEmailService userEmailService;

    @Autowired
    public UserEmailController(final Validator validator,
                               final UserEmailService userEmailService) {
        this.validator = validator;
        this.userEmailService = userEmailService;
    }

    @PutMapping("approved")
    public ResponseEntity<?> approvedEmail(@RequestBody final ApprovedEmailRequestDto dto){
        Set<ConstraintViolation<ApprovedEmailRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(4);
            for (ConstraintViolation<ApprovedEmailRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
            }
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        List<String> errorMessage = userEmailService.approvedUserEmail(dto.getEmail(), dto.getToken());
        if(errorMessage.isEmpty()){
            return ResponseEntity.ok(null);
        }
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("check/{email}")
    public ResponseEntity<?> emailBusyCheck(@PathVariable String email){
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.isValid(email,null)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.BAD_REQUEST,"Невалидные параметры запроса");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        if(userEmailService.emailBusyCheck(email)){
            AppResponseErrorDto errorDto =new AppResponseErrorDto(HttpStatus.CONFLICT,"email "+ email +" занят другим аккаунтом");
            return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
