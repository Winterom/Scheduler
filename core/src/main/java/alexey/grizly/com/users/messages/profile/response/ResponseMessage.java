package alexey.grizly.com.users.messages.profile.response;

import alexey.grizly.com.users.ws_handlers.EProfileWSEvents;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseMessage <T>{
    private String event;
    private MessagePayload<T> payload;

    public ResponseMessage(String event, T data, ResponseStatus status){
        this.event = event;
        MessagePayload<T> messagePayload = new MessagePayload<>();
        messagePayload.setData(data);
        messagePayload.setResponseStatus(status);
        this.payload = messagePayload;
    }
    public ResponseMessage(String event, List<String> errorMessages){
        this.event = event;
        MessagePayload  messagePayload = new MessagePayload<>();
        messagePayload.setErrorMessages(errorMessages);
        messagePayload.setResponseStatus(ResponseStatus.ERROR);
        this.payload = messagePayload;
    }
    public enum ResponseStatus{
        OK,
        ERROR
    }
    @Data
    public static class MessagePayload<T>{
        private ResponseStatus responseStatus;
        private List<String> errorMessages;
        private T data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("event", event)
                .append("payload", payload)
                .toString();
    }
}
