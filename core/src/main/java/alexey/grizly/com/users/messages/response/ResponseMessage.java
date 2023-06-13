package alexey.grizly.com.users.messages.response;

import alexey.grizly.com.users.ws_handlers.EWebsocketEvents;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMessage <T>{
    private EWebsocketEvents event;
    private MessagePayload<T> payload;

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
