package alexey.grizly.com.users.extractors;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.util.List;

public class UserAccountWithRoleExtractor implements ResultSetExtractor<List<UserAccount>> {
    @Override
    public List<UserAccount> extractData(ResultSet rs) throws DataAccessException {
        return null;
    }
}
