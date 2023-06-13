package alexey.grizly.com.users.extractors;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPasswordExtractor implements ResultSetExtractor<String> {
    @Override
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(!rs.next()){
            return null;
        }
        return rs.getString("password");
    }
}
