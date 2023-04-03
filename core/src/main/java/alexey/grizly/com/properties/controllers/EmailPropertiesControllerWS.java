package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;


@Controller
public class EmailPropertiesControllerWS {
    private final EmailPropertiesService emailPropertiesService;

    @Autowired
    public EmailPropertiesControllerWS(EmailPropertiesService emailPropertiesService) {
        this.emailPropertiesService = emailPropertiesService;
    }
    @SubscribeMapping("/emails")
    public void getMailList(){
        System.out.println("Прилетело сообщение");
    }
}
