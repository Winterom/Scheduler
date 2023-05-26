package alexey.grizly.com.mailing.services;




import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import alexey.grizly.com.properties.services.EmailPropertiesService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Getter
public class EmailSenders {
    private final EmailPropertiesService emailPropertiesService;
    private final Map<EEmailType, JavaMailSenderImpl> mailSenders;

    public EmailSenders(EmailPropertiesService emailPropertiesService) {
        this.emailPropertiesService = emailPropertiesService;
        this.mailSenders = new ConcurrentHashMap<>(EEmailType.values().length);
    }
    @PostConstruct
    public void init(){
        List<EmailPropertyModel> emails= emailPropertiesService.getActualProperties();
        emails.forEach(x->{
            this.mailSenders.put(x.getType(),createEmailSender(x));
        });
    }

    private JavaMailSenderImpl createEmailSender(EmailPropertyModel propertyModel){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        mailSender.setHost(propertyModel.getSmtpServer().getHost());
        mailSender.setUsername(propertyModel.getEmail());
        /*******************************************************/
        if(propertyModel.getSmtpServer().getRequireAuth()){
            mailSender.setPassword(propertyModel.getPassword());
            props.put("mail.smtp.auth", "true");
        }else {
            props.put("mail.smtp.auth", "false");
        }
        /*******************************************************/
        if(propertyModel.getSmtpServer().getProtocol().equals(EmailPropertyModel.SmtpServerProtocol.SSL)){
            props.put("mail.smtp.ssl.enable", "true");
            mailSender.setPort(propertyModel.getSmtpServer().getPortSSL());
        }else{
            props.put("mail.smtp.starttls.enable", "true");
            mailSender.setPort(propertyModel.getSmtpServer().getPortTLS());
        }

        /*******************************************************/
        /*TODO перенести в properties*/
        props.setProperty("mail.smtp.connectiontimeout", "5000");
        props.setProperty("mail.smtp.timeout", "5000");
        /*******************************************************/
        if(propertyModel.getIncomingServer().getServerType()
                .equals(EmailPropertyModel.IncomingServerType.IMAP)){
            setImapProperties(props,propertyModel.getIncomingServer());
            return mailSender;
        }
        if(propertyModel.getIncomingServer().getServerType()
                .equals(EmailPropertyModel.IncomingServerType.POP3)){
            setPop3Properties(props,propertyModel.getIncomingServer());
        }
        return mailSender;
    }

    private void setImapProperties(Properties props, EmailPropertyModel.IncomingServer model){
        props.setProperty("mail.imap.host", model.getImapServer());
        props.setProperty("mail.imap.port", model.getPortSSL().toString());
        props.setProperty("mail.imap.ssl.enable", model.getEnabledSSL().toString());
        /*TODO перенести в properties*/
        props.setProperty("mail.imap.connectiontimeout", "5000");
        props.setProperty("mail.imap.timeout", "5000");
    }
    private void setPop3Properties(Properties props, EmailPropertyModel.IncomingServer model){
        props.setProperty("mail.pop3.host", model.getImapServer());
        props.setProperty("mail.pop3.port", model.getPortSSL().toString());
        props.setProperty("mail.pop3.ssl.enable", model.getEnabledSSL().toString());
        /*TODO перенести в properties*/
        props.setProperty("mail.pop3.connectiontimeout", "5000");
        props.setProperty("mail.pop3.timeout", "5000");
    }
}
