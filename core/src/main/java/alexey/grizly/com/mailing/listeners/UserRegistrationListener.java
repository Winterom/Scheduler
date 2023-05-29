package alexey.grizly.com.mailing.listeners;

import alexey.grizly.com.commons.events.UserRegistrationEvent;
import alexey.grizly.com.mailing.services.EmailSenders;
import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.properties.GlobalProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener implements ApplicationListener<UserRegistrationEvent> {
    @Nullable
    private final JavaMailSender emailSender;
    private final GlobalProperties globalProperties;

    @Autowired
    public UserRegistrationListener(final EmailSenders emailSenders,
                                    final GlobalProperties globalProperties) {
        this.emailSender = emailSenders.getMailSenders().get(EEmailType.ADMIN_SENDER);
        this.globalProperties = globalProperties;
    }

    @Override
    public void onApplicationEvent(@NotNull UserRegistrationEvent event) {
        if(this.emailSender==null){
            return;
        }
        String url =globalProperties.getHost() + "approved?email="+event.getUserAccount().getEmail()
                +"&token="+ event.getToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getUserAccount().getEmail());
        message.setSubject("Регистрация в приложении");
        message.setText("Для подтверждения почты  " +
                " перейдите по ссылке " + url);
        emailSender.send(message);
    }
}
