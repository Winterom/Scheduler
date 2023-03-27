package alexey.grizly.com.properties.services;

import alexey.grizly.com.properties.models.EmailProperties;
import alexey.grizly.com.properties.models.GlobalProperties;
import alexey.grizly.com.properties.models.SecurityProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
    private final PropertiesConverter propertiesConverter;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GlobalProperties globalProperties;
    private final SecurityProperties securityProperties;
    private final EmailProperties emailProperties;

    @Autowired
    public PropertiesService(PropertiesConverter propertiesConverter, NamedParameterJdbcTemplate jdbcTemplate, GlobalProperties globalProperties, SecurityProperties securityProperties, EmailProperties emailProperties) {
        this.propertiesConverter = propertiesConverter;
        this.jdbcTemplate = jdbcTemplate;
        this.globalProperties = globalProperties;
        this.securityProperties = securityProperties;
        this.emailProperties = emailProperties;
    }

    public void updateProperty(Object property,Class<?> clazz){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("clazz", clazz.getName());
        namedParameters.addValue("property",propertiesConverter.convertToJson(property));
        jdbcTemplate.update("INSERT INTO properties VALUES (:clazz,:property) " +
                "ON CONFLICT (clazz) DO UPDATE SET property=:property",namedParameters);
    }

    @PostConstruct
    public void saveNewProperties(){
        updateProperty(emailProperties, EmailProperties.class);
        updateProperty(globalProperties, GlobalProperties.class);
        updateProperty(securityProperties,SecurityProperties.class);
    }
}
