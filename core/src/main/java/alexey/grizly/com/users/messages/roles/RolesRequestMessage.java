package alexey.grizly.com.users.messages.roles;

import alexey.grizly.com.users.messages.RequestMessage;
import alexey.grizly.com.users.messages.profile.request.RequestMessagePayloadDeserializer;
import alexey.grizly.com.users.ws_handlers.ERolesAuthoritiesWSEvents;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesRequestMessage implements RequestMessage {
    @JsonProperty("event")
    private ERolesAuthoritiesWSEvents event;
    @JsonProperty("data")
    @JsonDeserialize(using = RequestMessagePayloadDeserializer.class)
    private String data;

    public String getEvent() {
        return event.name();
    }

    public void setEvent(String event) {
        this.event = ERolesAuthoritiesWSEvents.valueOf(event);
    }
}
