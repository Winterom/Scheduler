package alexey.grizly.com.users.exceptions;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserErrorControllerAdvice {

    @ExceptionHandler(value = {LockedException.class,DisabledException.class})
    public ResponseEntity<AppResponseErrorDto> accountLockedCatch() {
        String errorMessage="Аккаунт заблокирован. Обратитесь к администратору";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = CredentialsExpiredException.class)
    public ResponseEntity<AppResponseErrorDto> credentialExpiredCatch() {
        String errorMessage="Срок действия пароля истек. Обратитесь к администратору";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<AppResponseErrorDto> badCredentialCatch() {
        String errorMessage="Не правильные учетные данные";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
}
