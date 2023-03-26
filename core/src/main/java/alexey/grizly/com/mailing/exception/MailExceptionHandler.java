package alexey.grizly.com.mailing.exception;

import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MailExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppResponseErrorDto> catchMailSenderNotPrepare(MailSenderNotPrepareException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppResponseErrorDto(HttpStatus.SERVICE_UNAVAILABLE,
                e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
