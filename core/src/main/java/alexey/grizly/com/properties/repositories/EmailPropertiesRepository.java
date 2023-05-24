package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailPropertiesRepository {
    List<EmailPropertyModel> getEnabledEmailProperties();
}
