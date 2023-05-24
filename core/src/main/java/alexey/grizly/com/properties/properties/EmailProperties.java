package alexey.grizly.com.properties.properties;

import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import alexey.grizly.com.properties.services.EmailPropertiesService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailProperties {
    private final EmailPropertiesService emailPropertiesService;
    private final Map<EEmailType,EmailPropertyModel> emailProperties;

    @Autowired
    public EmailProperties(EmailPropertiesService emailPropertiesService) {
        this.emailProperties = new ConcurrentHashMap<>(EEmailType.values().length);
        this.emailPropertiesService = emailPropertiesService;
    }
    @PostConstruct
    public void init(){

    }
}
