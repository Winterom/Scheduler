package alexey.grizly.com.properties.models;

import alexey.grizly.com.properties.services.PropertiesService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
@Component
public class EmailProperties {
    private String email;
    private String password;
    private OutgoingSmtpServer smtpServer;
    private IncomingServer incomingServer;
    @JsonIgnore
    private PropertiesService propertiesService;
    @JsonIgnore
    private final Lock writeLock;
    @JsonIgnore
    private final Lock readLock;

    public EmailProperties() {
        this.email="";
        this.password="";
        this.incomingServer = new IncomingServer();
        this.smtpServer = new OutgoingSmtpServer();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    @Autowired
    public EmailProperties(PropertiesService propertiesService) {
        this.email="";
        this.password="";
        this.incomingServer = new IncomingServer();
        this.smtpServer = new OutgoingSmtpServer();
        this.propertiesService = propertiesService;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }
    @PostConstruct
    public void init(){
        updateProperty((EmailProperties) propertiesService.getProperty(EmailProperties.class));
    }
    public void updateProperty(EmailProperties newProperty){
        try {
            this.writeLock.lock();
            this.email = newProperty.getEmail();
            this.password = newProperty.getPassword();
            this.incomingServer = newProperty.getIncomingServer();
            this.smtpServer = newProperty.getSmtpServer();
        }finally {
            this.writeLock.unlock();
        }
    }

    public String getEmail() {
        try {
            this.readLock.lock();
            return email;
        }finally {
            this.readLock.unlock();
        }
    }

    public String getPassword() {
        try {
            this.readLock.lock();
            return password;
        }finally {
            this.readLock.unlock();
        }
    }

    public OutgoingSmtpServer getSmtpServer() {
        try {
            this.readLock.lock();
            return smtpServer;
        }finally {
            this.readLock.unlock();
        }
    }

    public IncomingServer getIncomingServer() {
        try {
            this.readLock.lock();
            return incomingServer;
        }finally {
            this.readLock.unlock();
        }
    }

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
