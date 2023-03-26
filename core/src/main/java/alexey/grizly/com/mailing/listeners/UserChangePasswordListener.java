package alexey.grizly.com.mailing.listeners;


import alexey.grizly.com.commons.events.UserChangePasswordSendEmailEvent;
import alexey.grizly.com.mailing.service.AppMailSender;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class UserChangePasswordListener implements
        ApplicationListener<UserChangePasswordSendEmailEvent> {
    private final AppMailSender appMailSender;

    public UserChangePasswordListener(AppMailSender appMailSender) {
        this.appMailSender = appMailSender;
    }

    @Override
    public void onApplicationEvent(UserChangePasswordSendEmailEvent event) {
        eventHandler(event);
    }

    private void eventHandler(UserChangePasswordSendEmailEvent event){
        SimpleMailMessage message = new SimpleMailMessage();
        String from = appMailSender.getMailSender().getUsername();
        /*if(from==null){
            throw new MailSenderNotPrepareException("Отсутствуют настройки почты");
        }*/
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Регистрация на сайте");
        message
                .setText("Для завершения регистрации на сайте и подтверждения " +
                        "электронной почты перейдите по ссылке "+event.getReference());
        appMailSender.getMailSender().send(message);
    }
}
