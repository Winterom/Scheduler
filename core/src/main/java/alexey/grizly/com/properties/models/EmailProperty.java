package alexey.grizly.com.properties.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;



@Getter
@Setter
@Table(name = "email_properties")
public class EmailProperty {
    @Id
    private Long id;
    @Column(value = "type")
    private EEmailType type;
    @Column(value = "is_enabled")
    private Boolean isEnabled;
    @Column(value = "description")
    private String description;
    @Column(value = "email")
    private String email;
    @Column(value = "password")
    private String password;
    @Column(value = "alias")
    private String alias;
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
