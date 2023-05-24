package alexey.grizly.com.mailing.listeners;


import alexey.grizly.com.commons.events.UserPasswordChangeSendEmailEvent;
import alexey.grizly.com.mailing.services.EmailSenders;
import alexey.grizly.com.properties.models.EEmailType;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class UserPasswordChangeListener implements
        ApplicationListener<UserPasswordChangeSendEmailEvent> {

    @Nullable
    private final JavaMailSender emailSender;

    @Autowired
    public UserPasswordChangeListener(final EmailSenders emailSenders) {
        this.emailSender = emailSenders.getMailSenders().get(EEmailType.ADMIN_SENDER);
    }

    @Override
    public void onApplicationEvent(final UserPasswordChangeSendEmailEvent event) {
        eventHandler(event);
    }

    private void eventHandler(final UserPasswordChangeSendEmailEvent event){
        if(this.emailSender==null){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        /*String from = ;
        message.setFrom(from);*/
        message.setTo(event.getParam().getEmail());
        message.setSubject("Смена пароля");
        message.setText("Для смены пароля  " +
                        " перейдите по ссылке " + event.getParam().getChangePasswordUrl());
        emailSender.send(message);
    }
}
