package alexey.grizly.com.properties.extractors;

import alexey.grizly.com.properties.models.EEmailType;
import alexey.grizly.com.properties.models.EmailProperty;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailPropertyExtractor  implements ResultSetExtractor<EmailProperty> {
    @Override
    public EmailProperty extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(!rs.next()){
            return null;
        }
        EmailProperty property = new EmailProperty();
        property.setId(rs.getLong("id"));
        property.setEmail(rs.getString("email"));
        property.setType(EEmailType.valueOf(rs.getString("type")));
        property.setIsEnabled(rs.getBoolean("is_enabled"));
        property.setDescription(rs.getString("description"));
        property.setAlias(rs.getString("alias"));
        property.getSmtpServer().setHost(rs.getString("smtpHost"));
        property.getSmtpServer().setEnabledSSL(rs.getBoolean("smtpEnabledSSL"));
        property.getSmtpServer().setEnabledTLS(rs.getBoolean("smtpEnabledTLS"));
        property.getSmtpServer().setRequireAuth(rs.getBoolean("smtpRequireAuth"));
        property.getSmtpServer().setPortSSL(rs.getInt("smtpPortSSL"));
        property.getSmtpServer().setPortTLS(rs.getInt("smtpPortTLS"));
        property.getSmtpServer().setTransportProtocol(rs.getString("smtpTransportProtocol"));
        property.getIncomingServer().setServerType(EmailProperty.IncomingServerType.valueOf(rs.getString("incomingServerType")));
        property.getIncomingServer().setImapServer(rs.getString("incomingIMAPServer"));
        property.getIncomingServer().setEnabledSSL(rs.getBoolean("incomingEnabledSSL"));
        property.getIncomingServer().setPortSSL(rs.getInt("incomingPortSSL"));
        return property;
    }
}
