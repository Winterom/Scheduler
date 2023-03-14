package alexey.grizly.com.users.extractors;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;


public class UserAccountWithAuthoritiesExtractor implements ResultSetExtractor<UserAccount> {
    @Override
    public UserAccount extractData(ResultSet rs) throws DataAccessException {
        return null;
    }
}
