package alexey.grizly.com.properties.properties;

import alexey.grizly.com.properties.services.PropertiesConverter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class TempUnit {
    private final PropertiesConverter propertiesConverter;
    private final SecurityProperties securityProperties;

    public TempUnit(PropertiesConverter propertiesConverter, SecurityProperties securityProperties) {
        this.propertiesConverter = propertiesConverter;
        this.securityProperties = securityProperties;
    }
    @PostConstruct
    public void init(){
        System.out.println(propertiesConverter.convertToJson(securityProperties));
    }
}
