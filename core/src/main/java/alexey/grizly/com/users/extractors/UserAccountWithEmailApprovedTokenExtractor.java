package alexey.grizly.com.users.extractors;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.EmailApprovedToken;
import alexey.grizly.com.users.models.user.UserAccountWithEmailApprovedToken;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountWithEmailApprovedTokenExtractor implements ResultSetExtractor<UserAccountWithEmailApprovedToken> {
    @Override
    public UserAccountWithEmailApprovedToken extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(!rs.next()){
            return null;
        }
        UserAccountWithEmailApprovedToken userAccount = new UserAccountWithEmailApprovedToken();
        userAccount.setId(rs.getLong("id"));
        userAccount.setIsEmailVerified(rs.getBoolean("is_email_verified"));
        userAccount.setEmail(rs.getString("email"));
        userAccount.setStatus(EUserStatus.valueOf(rs.getString("e_status")));
        if(rs.getString("token")==null){
            userAccount.setToken(null);
            return userAccount;
        }
        EmailApprovedToken token = new EmailApprovedToken();
        token.setToken(rs.getString("token"));
        token.setExpired(rs.getTimestamp("token_expired").toLocalDateTime());
        token.setCreatedAt(rs.getTimestamp("token_created").toLocalDateTime());
        userAccount.setToken(token);
        return userAccount;
    }
}
