package alexey.grizly.com.mailing.listeners;


import alexey.grizly.com.commons.events.UserPasswordRestoreSendEmailEvent;
import alexey.grizly.com.mailing.services.AppMailSender;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Component
public class UserPasswordRestoreListener implements
        ApplicationListener<UserPasswordRestoreSendEmailEvent> {

    private final AppMailSender appMailSender;

    public UserPasswordRestoreListener(final AppMailSender appMailSender) {
        this.appMailSender = appMailSender;
    }

    @Override
    public void onApplicationEvent(final UserPasswordRestoreSendEmailEvent event) {
        eventHandler(event);
    }

    private void eventHandler(final UserPasswordRestoreSendEmailEvent event){
        SimpleMailMessage message = new SimpleMailMessage();
        String from = appMailSender.getMailSender().getUsername();
        message.setFrom(from);
        message.setTo(event.getParam().getEmail());
        message.setSubject("Регистрация на сайте");

        message
                .setText("Для завершения восстановления пароля  " +
                        " перейдите по ссылке " + event.getParam().getRestorePasswordUrl());
        /*appMailSender.getMailSender().send(message);*/
    }
}
