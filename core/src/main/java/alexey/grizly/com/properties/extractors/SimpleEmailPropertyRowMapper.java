package alexey.grizly.com.properties.extractors;


import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleEmailPropertyRowMapper implements RowMapper<EmailPropertyModel> {
    @Override
    public EmailPropertyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailPropertyModel emailPropertyModel = new EmailPropertyModel();
        emailPropertyModel.setId(rs.getLong("id"));
        emailPropertyModel.setEmail(rs.getString("email"));
        emailPropertyModel.setIsEnabled(rs.getBoolean("is_enabled"));
        emailPropertyModel.setDescription(rs.getString("description"));
        emailPropertyModel.setType(EEmailType.valueOf(rs.getString("type")));
       return emailPropertyModel;
    }
}
