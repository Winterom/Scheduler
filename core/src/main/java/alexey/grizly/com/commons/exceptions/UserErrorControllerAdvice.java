package alexey.grizly.com.commons.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class UserErrorControllerAdvice {

    private final PSQLErrorsTranslator errorsTranslator;
    public UserErrorControllerAdvice(PSQLErrorsTranslator errorsTranslator) {
        this.errorsTranslator = errorsTranslator;
    }

    @ExceptionHandler(value = {LockedException.class,DisabledException.class})
    public ResponseEntity<AppResponseErrorDto> accountLockedCatch() {
        String errorMessage="Аккаунт заблокирован. Обратитесь к администратору";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = CredentialsExpiredException.class)
    public ResponseEntity<AppResponseErrorDto> credentialExpiredCatch() {
        String errorMessage="Срок действия пароля истек. Воспользуйтесь восстановлением пароля";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<AppResponseErrorDto> badCredentialCatch() {
        String errorMessage="Не правильные учетные данные";
        AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = PSQLException.class)
    public ResponseEntity<AppResponseErrorDto> badSQLRequestCatch(PSQLException exception) {
        ServerErrorMessage serverErrorMessage = exception.getServerErrorMessage();
        AppResponseErrorDto errorDto;
        if(serverErrorMessage==null){
            errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,
                    exception.getMessage());
        }else {
            errorDto = new AppResponseErrorDto(HttpStatus.BAD_REQUEST,
                    this.errorsTranslator.getMessage(serverErrorMessage));
        }
        log.error(exception.getSQLState()+" "+exception.getServerErrorMessage().toString());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
