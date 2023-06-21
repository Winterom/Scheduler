package alexey.grizly.com.users.messages;

import alexey.grizly.com.users.ws_handlers.EProfileWSEvents;

public interface RequestMessage {
    String getEvent();

    String getData();

    void setEvent(String event);

    void setData(String data);
}
