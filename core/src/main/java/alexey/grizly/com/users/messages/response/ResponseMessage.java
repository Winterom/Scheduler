package alexey.grizly.com.users.messages.response;

import alexey.grizly.com.users.ws_handlers.WSREsponseEvents;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMessage <T>{
    @JsonProperty("event")
    private WSREsponseEvents event;
    @JsonProperty("data")
    private T data;
}
