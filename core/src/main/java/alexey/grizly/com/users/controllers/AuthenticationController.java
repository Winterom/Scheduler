package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.request.AuthRequestDto;
import alexey.grizly.com.users.dtos.response.AuthResponseDto;
import alexey.grizly.com.users.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Validator validator;
    private static final String NOT_VALID_PARAMS = "Не правильные учетные данные";

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationService, final Validator validator) {
        this.authenticationService = authenticationService;
        this.validator = validator;
    }


    /*Почта email1@one.ru Пароль 2012 */

    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody final AuthRequestDto authRequest, final HttpServletResponse response) {
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(authRequest);
        if(!violations.isEmpty()) {
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,NOT_VALID_PARAMS);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        String accessToken = authenticationService.authentication(authRequest.getEmailOrPhone()
                ,authRequest.getPassword(),response);
        return ResponseEntity.ok(new AuthResponseDto(accessToken));
    }

    @GetMapping
    public ResponseEntity<?> refresh(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,NOT_VALID_PARAMS);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        Optional<Cookie> refreshToken =Arrays.stream(cookies).filter(x-> x.getName().equals("token")).findFirst();
        if(refreshToken.isEmpty()){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,NOT_VALID_PARAMS);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        String accessToken = authenticationService.authenticationByRefreshToken(refreshToken.get());
        if(accessToken==null){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,NOT_VALID_PARAMS);
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(new AuthResponseDto(accessToken));
    }

}
