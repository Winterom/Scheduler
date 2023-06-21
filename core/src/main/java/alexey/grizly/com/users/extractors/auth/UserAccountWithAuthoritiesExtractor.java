package alexey.grizly.com.users.extractors.auth;

import alexey.grizly.com.commons.security.EAuthorities;
import alexey.grizly.com.users.models.AppAuthorities;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.user.UserAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;


public class UserAccountWithAuthoritiesExtractor implements ResultSetExtractor<UserAccount> {
    @Override
    public UserAccount extractData(ResultSet rs) throws DataAccessException, SQLException {
        if(!rs.next()){
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setAuthorities(new HashSet<>());
        userAccount.setEmail(rs.getString("email"));
        userAccount.setId(rs.getLong("id"));
        userAccount.setStatus(EUserStatus.valueOf(rs.getString("e_status")));
        userAccount.setPassword(rs.getString("password"));
        userAccount.setCredentialExpiredTime(rs.getTimestamp("credential_expired").toLocalDateTime());
        AppAuthorities authorities = new AppAuthorities();
        String auth2 = rs.getString("e_authorities");
        System.out.println(auth2);
        authorities.setAuthorities(EAuthorities.valueOf(auth2));
        userAccount.getAuthorities().add(authorities);
        while (rs.next()){
            AppAuthorities auth = new AppAuthorities();
            auth.setAuthorities(EAuthorities.valueOf(rs.getString("e_authorities")));
            userAccount.getAuthorities().add(auth);
        }
        return userAccount;
    }
}
