package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorityNodeRowMapper implements RowMapper<AuthoritiesNode.Authority> {
    @Override
    public AuthoritiesNode.Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthoritiesNode.Authority node = new AuthoritiesNode.Authority();
        node.setKey(rs.getLong("id"));
        node.setLabel(rs.getString("title"));
        node.setDescription(rs.getString("description"));
        node.setIsCatalog(rs.getBoolean("is_catalog"));
        node.setParentId(rs.getLong("catalog"));
        return node;
    }
}
