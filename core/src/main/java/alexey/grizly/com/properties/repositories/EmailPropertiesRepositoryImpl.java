package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.extractors.EmailPropertyRowMapper;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmailPropertiesRepositoryImpl implements EmailPropertiesRepository{
    private final String SELECT_ENABLED_PROP="SELECT * FROM email_properties as p WHERE is_enabled = true";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EmailPropertiesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EmailPropertyModel> getEnabledEmailProperties() {
        return jdbcTemplate.getJdbcTemplate().query(SELECT_ENABLED_PROP,new EmailPropertyRowMapper());
    }
}
