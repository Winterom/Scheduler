package alexey.grizly.com.mailing.listeners;


import alexey.grizly.com.commons.events.UserPasswordChangeSendEmailEvent;
import alexey.grizly.com.mailing.services.AdminMailSender;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
public class UserPasswordChangeListener implements
        ApplicationListener<UserPasswordChangeSendEmailEvent> {

    private final AdminMailSender adminMailSender;

    public UserPasswordChangeListener(final AdminMailSender adminMailSender) {
        this.adminMailSender = adminMailSender;
    }

    @Override
    public void onApplicationEvent(final UserPasswordChangeSendEmailEvent event) {
        eventHandler(event);
    }

    private void eventHandler(final UserPasswordChangeSendEmailEvent event){
        SimpleMailMessage message = new SimpleMailMessage();
        String from = adminMailSender.getMailSender().getUsername();
        message.setFrom(from);
        message.setTo(event.getParam().getEmail());
        message.setSubject("Восстановление пароля");
        message.setText("Для восстановления пароля  " +
                        " перейдите по ссылке " + event.getParam().getChangePasswordUrl());
        adminMailSender.getMailSender().send(message);
    }
}
