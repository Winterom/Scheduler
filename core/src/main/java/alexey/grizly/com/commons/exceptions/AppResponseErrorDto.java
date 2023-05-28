package alexey.grizly.com.commons.exceptions;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AppResponseErrorDto {
    private HttpStatusCode statusCode;
    private List<String> messages;

    public AppResponseErrorDto (HttpStatusCode statusCode,String message){
        this.statusCode = statusCode;
        this.messages = new ArrayList<>(1);
        this.messages.add(message);
    }
    public AppResponseErrorDto (HttpStatusCode statusCode,List<String> message){
        this.statusCode = statusCode;
        this.messages = message;
    }
}
