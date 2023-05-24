package alexey.grizly.com.properties.services;

import alexey.grizly.com.properties.models.PropertiesModel;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface PropertiesService {
    @Nullable Object getProperty(Class<?> clazz);

    @Transactional
    boolean updateProperty(PropertiesModel model);
}
