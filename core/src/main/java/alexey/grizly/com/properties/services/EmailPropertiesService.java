package alexey.grizly.com.properties.services;

import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmailPropertiesService {
    List<EmailPropertyModel> getActualProperties();
}
