package alexey.grizly.com.properties.controllers;

import alexey.grizly.com.properties.dto.security.response.PasswordStrangeResponseDto;
import alexey.grizly.com.properties.models.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/properties/security")
public class SecurityPropertiesController {
    private final SecurityProperties securityProperties;

    @Autowired
    public SecurityPropertiesController(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @GetMapping("/password/strange")
    public PasswordStrangeResponseDto getPasswordStrangeContract(){
        return new PasswordStrangeResponseDto(this.securityProperties.getUserPasswordStrange());
    }
}
