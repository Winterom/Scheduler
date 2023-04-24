package alexey.grizly.com.properties.controllers;


import alexey.grizly.com.properties.dtos.security.responses.EmailPropertiesTableResponseDto;
import alexey.grizly.com.properties.models.EmailProperty;
import alexey.grizly.com.properties.services.EmailPropertiesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    public List<EmailPropertiesTableResponseDto> getEmailsPropertiesList (@RequestParam MultiValueMap<String,String> params){
        System.out.println(params);
        List<EmailProperty> emailProperties = emailPropertiesService.getEmailPropertyList();
        return emailProperties.stream().map(EmailPropertiesTableResponseDto::new).collect(Collectors.toList());
    }



}
