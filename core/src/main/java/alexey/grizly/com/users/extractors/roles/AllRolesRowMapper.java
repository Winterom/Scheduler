package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RoleNode;
import alexey.grizly.com.users.models.ERoleStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllRolesRowMapper implements RowMapper<RoleNode.Role> {
    @Override
    public RoleNode.Role mapRow(ResultSet rs, int rowNum) throws SQLException {
       RoleNode.Role role = new RoleNode.Role();
       role.setKey(rs.getLong("id"));
       role.setIsCatalog(rs.getBoolean("is_catalog"));
       role.setParentId(rs.getLong("parent_id"));
       role.setLabel(rs.getString("title"));
       role.setStatus(ERoleStatus.valueOf(rs.getString("status")));
       role.setDescription(rs.getString("description"));
       role.setCreatedAt(rs.getTimestamp("createdat").toLocalDateTime());
       role.setUpdatedAt(rs.getTimestamp("updatedat").toLocalDateTime());
       role.setPath(rs.getString("path"));
       role.setModifyBy(rs.getString("email"));
       return role;
    }
}
