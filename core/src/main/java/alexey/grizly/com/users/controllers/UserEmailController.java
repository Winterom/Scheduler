package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.request.ApprovedEmailRequestDto;
import alexey.grizly.com.users.services.UserEmailService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(userEmailService.approvedUserEmail(dto.getEmail(), dto.getToken())){
            return ResponseEntity.ok(null);
        }
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE,"Неверные учетные данные пользователя");
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
    }
}
