package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/emails")
public class EmailPropertiesController {
    private final EmailPropertiesService emailPropertiesService;
    @Autowired
    public EmailPropertiesController(EmailPropertiesService emailPropertiesService) {
        this.emailPropertiesService = emailPropertiesService;
    }


}
