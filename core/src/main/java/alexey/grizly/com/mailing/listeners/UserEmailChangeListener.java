package alexey.grizly.com.mailing.listeners;

import alexey.grizly.com.mailing.services.EmailSenders;
import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.users.events.UserEmailApprovedTokenEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserEmailChangeListener implements ApplicationListener<UserEmailApprovedTokenEvent> {
    @Nullable
    private final JavaMailSender emailSender;
    private final GlobalProperties globalProperties;

    @Autowired
    public UserEmailChangeListener(final EmailSenders emailSenders,
                                   final GlobalProperties globalProperties) {
        this.emailSender = emailSenders.getMailSenders().get(EEmailType.ADMIN_SENDER);
        this.globalProperties = globalProperties;
    }

    @Override
    public void onApplicationEvent(@NotNull UserEmailApprovedTokenEvent event) {
        if(this.emailSender==null){
            return;
        }
        if(event.getUserAccount().getToken()==null){
            return;
        }
        String token = event.getUserAccount().getToken().getToken();
        String url =globalProperties.getHost() + "approved?email="+event.getUserAccount().getEmail()
                +"&token="+ token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getUserAccount().getEmail());
        message.setSubject("Подтверждение почты");
        message.setText("Для подтверждения почты  " +
                " перейдите по ссылке " + url);
        emailSender.send(message);
    }
}
