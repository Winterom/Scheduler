package alexey.grizly.com.mailing.listeners;

import alexey.grizly.com.commons.events.UserRegistrationEvent;
import alexey.grizly.com.mailing.services.EmailSenders;
import alexey.grizly.com.properties.models.EEmailType;
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

    @Autowired
    public UserRegistrationListener(final EmailSenders emailSenders) {
        this.emailSender = emailSenders.getMailSenders().get(EEmailType.ADMIN_SENDER);
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        if(this.emailSender==null){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        /*String from = ;
        message.setFrom(from);*/
        message.setTo(event.getEventParam().getUserAccount().getEmail());
        message.setSubject("Регистрация в приложении");
        message.setText("Для подтверждения почты  " +
                " перейдите по ссылке " + event.getEventParam().getUrl());
        emailSender.send(message);
    }
}
