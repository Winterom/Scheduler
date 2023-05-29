package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.request.UserRegistrationRequestDto;
import alexey.grizly.com.users.services.UserRegistrationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users/registration")
public class UserRegistrationController {
    private final Validator validator;
    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(final Validator validator,
                                      final UserRegistrationService userRegistrationService) {
        this.validator = validator;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody final UserRegistrationRequestDto dto){
        Set<ConstraintViolation<UserRegistrationRequestDto>> violations = validator.validate(dto);
        if(!violations.isEmpty()) {
            List<String> errorMessage = new ArrayList<>(6);
            for (ConstraintViolation<UserRegistrationRequestDto> violation : violations) {
                errorMessage.add(violation.getMessage());
            }
            AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,errorMessage);
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        this.userRegistrationService.createNewUserAccount(dto.getEmail(),
                dto.getPhone(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getLastname());
        return ResponseEntity.ok(null);
    }
}
