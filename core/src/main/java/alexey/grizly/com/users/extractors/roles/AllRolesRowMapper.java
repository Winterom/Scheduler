package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RoleByGroups;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllRolesRowMapper implements RowMapper<RoleByGroups> {
    @Override
    public RoleByGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
       RoleByGroups role = new RoleByGroups();
       role.setId(rs.getLong("id"));
       role.setIsCatalog(rs.getBoolean("is_catalog"));
       role.setParentId(rs.getLong("catalog"));
       role.setTitle(rs.getString("title"));
       role.setDescription(rs.getString("description"));
       role.setCreatedAt(rs.getTimestamp("createdat").toLocalDateTime());
       role.setUpdatedAt(rs.getTimestamp("updatedat").toLocalDateTime());
       role.setModifyBy(rs.getString("email"));
       return role;
    }
}
