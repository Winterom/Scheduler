package alexey.grizly.com.users.messages.profile.response;

import alexey.grizly.com.users.ws_handlers.EProfileWSEvents;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseMessage <T>{
    private EProfileWSEvents event;
    private MessagePayload<T> payload;

    public ResponseMessage(EProfileWSEvents event, T data, ResponseStatus status){
        this.event = event;
        MessagePayload<T> messagePayload = new MessagePayload<>();
        messagePayload.setData(data);
        messagePayload.setResponseStatus(status);
        this.payload = messagePayload;
    }
    public ResponseMessage(EProfileWSEvents event, List<String> errorMessages){
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
}
