package alexey.grizly.com.users.messages.request;

import alexey.grizly.com.users.ws_handlers.WSRequestEvents;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {
    @JsonProperty("event")
    private WSRequestEvents event;
    @JsonProperty("data")
    private String data;
}
