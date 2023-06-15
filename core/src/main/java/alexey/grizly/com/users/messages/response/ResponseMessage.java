package alexey.grizly.com.users.messages.response;

import alexey.grizly.com.users.ws_handlers.EWebsocketEvents;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseMessage <T>{
    private EWebsocketEvents event;
    private MessagePayload<T> payload;

    public ResponseMessage(EWebsocketEvents event,T data,ResponseStatus status){
        this.event = event;
        MessagePayload<T> messagePayload = new MessagePayload<>();
        messagePayload.setData(data);
        messagePayload.setResponseStatus(status);
        this.payload = messagePayload;
    }
    public ResponseMessage(EWebsocketEvents event,List<String> errorMessages){
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
