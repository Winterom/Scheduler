package alexey.grizly.com.properties.extractors;


import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailProperty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleEmailPropertyRowMapper implements RowMapper<EmailProperty> {
    @Override
    public EmailProperty mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailProperty emailProperty = new EmailProperty();
        emailProperty.setId(rs.getLong("id"));
        emailProperty.setEmail(rs.getString("email"));
        emailProperty.setIsEnabled(rs.getBoolean("is_enabled"));
        emailProperty.setDescription(rs.getString("description"));
        emailProperty.setType(EEmailType.valueOf(rs.getString("type")));
       return emailProperty;
    }
}
