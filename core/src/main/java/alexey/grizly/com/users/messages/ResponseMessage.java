package alexey.grizly.com.users.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMessage <T>{
    @JsonProperty("event")
    private String event;
    @JsonProperty("data")
    private T data;
}
