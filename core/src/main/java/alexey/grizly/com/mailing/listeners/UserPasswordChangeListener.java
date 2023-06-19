package alexey.grizly.com.mailing.listeners;


import alexey.grizly.com.users.events.UserPasswordChangeCreateTokenEvent;
import alexey.grizly.com.mailing.services.EmailSenders;
import alexey.grizly.com.properties.models.EEmailType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class UserPasswordChangeListener implements
        ApplicationListener<UserPasswordChangeCreateTokenEvent> {

    @Nullable
    private final JavaMailSender emailSender;

    @Autowired
    public UserPasswordChangeListener(final EmailSenders emailSenders) {
        this.emailSender = emailSenders.getMailSenders().get(EEmailType.ADMIN_SENDER);
    }

    @Override
    public void onApplicationEvent(final @NotNull UserPasswordChangeCreateTokenEvent event) {
        eventHandler(event);
    }

    private void eventHandler(final UserPasswordChangeCreateTokenEvent event){
        if(this.emailSender==null){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getParam().getEmail());
        message.setSubject("Смена пароля");
        message.setText("Для смены пароля  " +
                        " перейдите по ссылке " + event.getParam().getChangePasswordUrl());
        emailSender.send(message);
    }
}
