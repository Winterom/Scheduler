package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.dtos.security.responses.EmailPropertiesTableResponseDto;
import alexey.grizly.com.properties.models.EmailProperty;
import alexey.grizly.com.properties.services.EmailPropertiesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/emails")
public class EmailPropertiesController {
    private final EmailPropertiesService emailPropertiesService;
    @Autowired
    public EmailPropertiesController(EmailPropertiesService emailPropertiesService) {
        this.emailPropertiesService = emailPropertiesService;
    }

    @GetMapping("/table")
    public List<EmailPropertiesTableResponseDto> getEmailsPropertiesList (HttpServletRequest request){
        System.out.println(request.getQueryString());
        List<EmailProperty> emailProperties = emailPropertiesService.getEmailPropertyList();
        return emailProperties.stream().map(EmailPropertiesTableResponseDto::new).collect(Collectors.toList());
    }


}
