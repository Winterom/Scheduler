package alexey.grizly.com.properties.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailProperties {
    private String email;
    private String password;
    private OutgoingSmtpServer smtpServer;
    private IncomingServer incomingServer;

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
