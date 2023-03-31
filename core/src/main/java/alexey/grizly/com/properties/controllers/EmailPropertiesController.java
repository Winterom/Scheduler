package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
/*@RequestMapping("api/v1/properties/mailing")*/
public class EmailPropertiesController {
    private final EmailPropertiesService emailPropertiesService;

    @Autowired
    public EmailPropertiesController(EmailPropertiesService emailPropertiesService) {
        this.emailPropertiesService = emailPropertiesService;
    }


    public void getMailList(){

    }
}
