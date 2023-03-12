package alexey.grizly.com.commons.errors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


public class AppResponseErrorDto{
    private final HttpStatusCode statusCode;
    private final String message;
    public AppResponseErrorDto(HttpStatusCode statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
    public ResponseEntity<?> getResponseEntity(){
        return new ResponseEntity<>(message,statusCode);
    }
}
