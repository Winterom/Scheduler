package alexey.grizly.com.properties.services.impl;

import alexey.grizly.com.properties.models.EmailPropertyModel;
import alexey.grizly.com.properties.repositories.EmailPropertiesRepository;
import alexey.grizly.com.properties.services.EmailPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailPropertiesServiceImpl implements EmailPropertiesService {
    private final EmailPropertiesRepository propertyRepository;
    @Autowired
    public EmailPropertiesServiceImpl(EmailPropertiesRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<EmailPropertyModel> getActualProperties() {
        return propertyRepository.getEnabledEmailProperties();
    }
}
