package alexey.grizly.com.users.messages.response;

import alexey.grizly.com.users.ws_handlers.WSResponseEvents;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMessage <T>{
    @JsonProperty("event")
    private WSResponseEvents event;
    @JsonProperty("data")
    private T data;
}
