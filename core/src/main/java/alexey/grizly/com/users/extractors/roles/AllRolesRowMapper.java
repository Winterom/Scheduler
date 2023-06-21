package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RolesByGroups;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllRolesRowMapper implements RowMapper<RolesByGroups> {
    @Override
    public RolesByGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
