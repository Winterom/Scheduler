package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;


@Controller
public class EmailPropertiesControllerWS {
    private final EmailPropertiesService emailPropertiesService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EmailPropertiesControllerWS(EmailPropertiesService emailPropertiesService, SimpMessagingTemplate messagingTemplate) {
        this.emailPropertiesService = emailPropertiesService;
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/emails")

    public void getMailList(){
        System.out.println("Прилетело сообщение: ");
        messagingTemplate.convertAndSend("/events/emails","Здесь будет таблица");
    }
    @SubscribeMapping("/events/emails")
    public void getMailList2(){
        System.out.println("Прилетело сообщение при подписке: ");
        messagingTemplate.convertAndSend("/events/emails","Здесь будет таблица при подписке");
    }
}
