package alexey.grizly.com.properties.services;

import alexey.grizly.com.properties.models.EmailPropertyModel;
import alexey.grizly.com.properties.repositories.EmailPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailPropertiesService {
    private final EmailPropertiesRepository propertyRepository;
    @Autowired
    public EmailPropertiesService(EmailPropertiesRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    public List<EmailPropertyModel> getEmailPropertyList(){
        return propertyRepository.getAllProperties();
    }
}
