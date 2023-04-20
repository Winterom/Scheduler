package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.models.EmailProperty;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailPropertiesRepository extends org.springframework
        .data.repository.Repository<EmailProperty,Long>{
    @Query("SELECT * FROM email_properties")
    List<EmailProperty> getAllProperties();
}
