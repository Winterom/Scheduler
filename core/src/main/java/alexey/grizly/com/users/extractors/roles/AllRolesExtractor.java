package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RoleNode;
import alexey.grizly.com.users.models.ERoleStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;

public class AllRolesExtractor implements ResultSetExtractor<RoleNode.Role> {
    @Override
    public RoleNode.Role extractData(ResultSet rs) throws SQLException, DataAccessException {
        Deque<RoleNode.Role> roleDeque = new LinkedList<>();

        RoleNode.Role role = mapRow(rs);
        return role;
    }

    public RoleNode.Role mapRow(ResultSet rs) throws SQLException {
        RoleNode.Role role = new RoleNode.Role();
        role.setKey(rs.getLong("id"));
        role.setIsCatalog(rs.getBoolean("is_catalog"));
        role.setParentId(rs.getLong("parent_id"));
        role.setLabel(rs.getString("title"));
        role.setStatus(ERoleStatus.valueOf(rs.getString("status")));
        role.setDescription(rs.getString("description"));
        role.setCreatedAt(rs.getTimestamp("createdat").toLocalDateTime());
        role.setUpdatedAt(rs.getTimestamp("updatedat").toLocalDateTime());
        String path = rs.getString("path").substring(1);
        role.setPath(path);
        role.setModifyBy(rs.getString("email"));
        return role;
    }
}
