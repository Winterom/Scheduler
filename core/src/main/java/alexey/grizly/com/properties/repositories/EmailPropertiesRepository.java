package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.models.EmailProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailPropertiesRepository {

    List<EmailProperty> getAllProperties();
}
