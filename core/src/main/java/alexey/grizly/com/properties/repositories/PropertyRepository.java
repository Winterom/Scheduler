package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.models.PropertiesModel;
import org.springframework.stereotype.Repository;


@Repository
public interface PropertyRepository extends org.springframework
        .data.repository.Repository<PropertiesModel,Long>{


}
