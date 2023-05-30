package alexey.grizly.com.users.extractors;


import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.PasswordChangeToken;
import alexey.grizly.com.users.models.UserAccountWithPasswordChangeToken;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountWithPasswordChangeTokenExtractor implements ResultSetExtractor<UserAccountWithPasswordChangeToken> {
    @Override
    public UserAccountWithPasswordChangeToken extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(!rs.next()){
            return null;
        }
        UserAccountWithPasswordChangeToken userAccount = new UserAccountWithPasswordChangeToken();
        userAccount.setId(rs.getLong("id"));
        userAccount.setStatus(EUserStatus.valueOf(rs.getString("e_status")));
        userAccount.setPassword(rs.getString("password"));
        if(rs.getString("token")==null){
            userAccount.setPasswordChangeToken(null);
            return userAccount;
        }
        PasswordChangeToken token = new PasswordChangeToken();
        token.setToken(rs.getString("token"));
        token.setExpired(rs.getTimestamp("token_expired").toLocalDateTime());
        token.setCreatedAt(rs.getTimestamp("token_created").toLocalDateTime());
        userAccount.setPasswordChangeToken(token);
        return userAccount;
    }
}
