package alexey.grizly.com.properties.models;


import lombok.Getter;
import lombok.Setter;




@Getter
@Setter

public class EmailProperty {

    private Long id;
    private EEmailType type;
    private Boolean isEnabled;
    private String description;
    private String email;
    private String password;
    private OutgoingSmtpServer smtpServer = new OutgoingSmtpServer();
    private IncomingServer incomingServer = new IncomingServer();

    @Getter
    @Setter
    public static class OutgoingSmtpServer{
        private String host;
        private Boolean enabledSSL;
        private Boolean enabledTLS;
        private Boolean requireAuth;
        private Integer portSSL;
        private Integer portTLS;
        private String transportProtocol;
    }

    @Getter
    @Setter
    public static class IncomingServer{
        private IncomingServerType serverType;
        private String imapServer;
        private Boolean enabledSSL;
        private Integer portSSL;
    }

    public enum IncomingServerType{
        IMAP,POP3
    }
}
