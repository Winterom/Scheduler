package alexey.grizly.com.mailing.service;




import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;



@Service
@Getter
public class AppMailSender {
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

}
