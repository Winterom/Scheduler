package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.ERoleStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllRolesRowMapper implements RowMapper<RolesTree.RoleNode> {
    @Override
    public RolesTree.RoleNode mapRow(ResultSet rs, int rowNum) throws SQLException {
       RolesTree.RoleNode role = new RolesTree.RoleNode();
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
