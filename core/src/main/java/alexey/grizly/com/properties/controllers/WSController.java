package alexey.grizly.com.properties.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/emails")
    @SendTo("/topic/messages")
    public String getProp(){
        return "ura....urq";
    }
}
