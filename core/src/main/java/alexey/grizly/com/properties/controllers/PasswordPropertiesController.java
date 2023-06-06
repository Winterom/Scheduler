package alexey.grizly.com.properties.controllers;

import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;
import alexey.grizly.com.properties.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/properties/security")
public class PasswordPropertiesController {
    private final SecurityProperties securityProperties;


    @Autowired
    public PasswordPropertiesController(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @GetMapping("/password/strange")
    public PasswordStrengthResponseDto getPasswordStrangeContract(){
        return new PasswordStrengthResponseDto(this.securityProperties.getUserPasswordStrange());
    }
}
