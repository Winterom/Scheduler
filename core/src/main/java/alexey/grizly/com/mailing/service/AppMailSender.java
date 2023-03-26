package alexey.grizly.com.mailing.service;


import alexey.grizly.com.commons.events.UserChangePasswordSendEmailEvent;

import lombok.Getter;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;



@Service
@Getter
public class AppMailSender implements
        ApplicationListener<UserChangePasswordSendEmailEvent>{
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


    @Override
    public void onApplicationEvent(UserChangePasswordSendEmailEvent event) {

    }
}
