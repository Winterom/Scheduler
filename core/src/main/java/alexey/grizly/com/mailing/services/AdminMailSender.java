package alexey.grizly.com.mailing.services;




import alexey.grizly.com.properties.properties.EmailProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;



@Service
@Getter
public class AdminMailSender {
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private final EmailProperties emailProperties;
    @Autowired
    public AdminMailSender(EmailProperties emailProperties){
        this.emailProperties = emailProperties;
    }
}
