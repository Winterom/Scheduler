package alexey.grizly.com.users.messages;

public interface RequestMessage {
    String getEvent();

    String getData();

    void setEvent(String event);

    void setData(String data);
}
