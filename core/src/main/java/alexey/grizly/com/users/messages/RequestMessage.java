package alexey.grizly.com.users.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestMessage {
    @JsonProperty("event")
    private String event;
    @JsonProperty("data")
    private String data;

    public RequestMessage() {
    }

    public RequestMessage(String event, String data) {
        this.event = event;
        this.data = data;
    }
}
