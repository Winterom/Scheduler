package alexey.grizly.com.users.extractors;

import alexey.grizly.com.commons.security.EAuthorities;
import alexey.grizly.com.commons.security.EUserStatus;
import alexey.grizly.com.users.models.AppAuthorities;
import alexey.grizly.com.users.models.UserAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;


public class UserAccountWithAuthoritiesExtractor implements ResultSetExtractor<UserAccount> {
    @Override
    public UserAccount extractData(ResultSet rs) throws DataAccessException, SQLException {
        UserAccount userAccount = new UserAccount();
        userAccount.setAuthorities(new HashSet<>());
        rs.next();
        userAccount.setEmail(rs.getString("email"));
        userAccount.setId(rs.getLong("id"));
        userAccount.setStatus(EUserStatus.valueOf(rs.getString("e_status")));
        userAccount.setPassword(rs.getString("password"));
        userAccount.setCredentialExpiredTime(rs.getTimestamp("credential_expired").toLocalDateTime());
        while (rs.next()){
            AppAuthorities authorities = new AppAuthorities();
            authorities.setAuthorities(EAuthorities.valueOf(rs.getString("e_authorities")));
            userAccount.getAuthorities().add(authorities);
        }
        System.out.println(userAccount);
        return userAccount;
    }
}
