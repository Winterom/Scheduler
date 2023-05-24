package alexey.grizly.com.properties.extractors;

import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailPropertyModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailPropertyRowMapper implements RowMapper<EmailPropertyModel> {

    @Override
    public EmailPropertyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailPropertyModel property = new EmailPropertyModel();
        property.setId(rs.getLong("id"));
        property.setEmail(rs.getString("email"));
        property.setType(EEmailType.valueOf(rs.getString("type")));
        property.setIsEnabled(rs.getBoolean("is_enabled"));
        property.setDescription(rs.getString("description"));
        property.getSmtpServer().setHost(rs.getString("smtpHost"));
        property.getSmtpServer().setEnabledSSL(rs.getBoolean("smtpEnabledSSL"));
        property.getSmtpServer().setEnabledTLS(rs.getBoolean("smtpEnabledTLS"));
        property.getSmtpServer().setRequireAuth(rs.getBoolean("smtpRequireAuth"));
        property.getSmtpServer().setPortSSL(rs.getInt("smtpPortSSL"));
        property.getSmtpServer().setPortTLS(rs.getInt("smtpPortTLS"));
        property.getSmtpServer().setTransportProtocol(rs.getString("smtpTransportProtocol"));
        property.getIncomingServer().setServerType(EmailPropertyModel.IncomingServerType.valueOf(rs.getString("incomingServerType")));
        property.getIncomingServer().setImapServer(rs.getString("incomingIMAPServer"));
        property.getIncomingServer().setEnabledSSL(rs.getBoolean("incomingEnabledSSL"));
        property.getIncomingServer().setPortSSL(rs.getInt("incomingPortSSL"));
        return property;
    }
}
