package alexey.grizly.com.properties.models;


import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class EmailPropertyModel {
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
        private Boolean requireAuth;
        private SmtpServerProtocol protocol;
        private Integer portSSL;
        private Integer portTLS;
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

    public enum SmtpServerProtocol{
        SSL,TLS
    }
}
