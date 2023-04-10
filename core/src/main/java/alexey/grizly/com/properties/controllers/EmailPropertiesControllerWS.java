package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class EmailPropertiesControllerWS {
    private final EmailPropertiesService emailPropertiesService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EmailPropertiesControllerWS(EmailPropertiesService emailPropertiesService, SimpMessagingTemplate messagingTemplate) {
        this.emailPropertiesService = emailPropertiesService;
        this.messagingTemplate = messagingTemplate;
    }
   /* @PreAuthorize(value = "hasAuthority('GLOBAL_SETTINGS_READ')")*/
    @MessageMapping("/emails")
    public void getMailList(Principal principal){
        System.out.println(principal);
        System.out.println("Прилетело сообщение: ");
        messagingTemplate.convertAndSend("/events/emails","Здесь будет таблица");
    }
    @SubscribeMapping("/events/emails")
    public void getMailList2(){
        System.out.println("Прилетело сообщение при подписке: ");
        messagingTemplate.convertAndSend("/events/emails","Здесь будет таблица при подписке");
    }
}
