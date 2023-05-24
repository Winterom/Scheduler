package alexey.grizly.com.properties.repositories;

import alexey.grizly.com.properties.extractors.SimpleEmailPropertyRowMapper;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmailPropertiesRepositoryImpl implements EmailPropertiesRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EmailPropertiesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EmailPropertyModel> getAllProperties() {
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        return jdbcTemplate.query("SELECT id,email,is_enabled,type,description FROM email_properties",namedParameters,new SimpleEmailPropertyRowMapper());
    }

    @Override
    public List<EmailPropertyModel> getEnabledEmailProperties() {
        return null;
    }
}
