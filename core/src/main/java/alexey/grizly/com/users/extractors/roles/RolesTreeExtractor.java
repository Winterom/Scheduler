package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.ERoleStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Slf4j
public class RolesTreeExtractor implements ResultSetExtractor<RolesTree.RoleNode> {
    @Override
    public RolesTree.RoleNode extractData(ResultSet rs) throws SQLException, DataAccessException {
        RolesTree.RoleNode startNode = null;
        final Queue<RolesTree.RoleNode> rolesLeafs = new LinkedList<>();
        final Map<Long, RolesTree.RoleNode> rolesNodes = new HashMap<>();
        while (rs.next()){
            RolesTree.RoleNode role = mapRow(rs);
            if(role.getParentId()==0){
                startNode = role;
                startNode.setChildren(new LinkedList<>());
            }else {
               if(Boolean.TRUE.equals(role.getIsCatalog())){
                   role.setChildren(new LinkedList<>());
                   rolesNodes.put(role.getKey(),role);
               }else {
                   rolesLeafs.add(role);
               }
            }
        }
        if(startNode==null){
            log.error("Не нашли рутовую ноду");
            StringBuilder result = new StringBuilder();
            rolesLeafs.forEach(x-> result.append(" ").append(x));
            rolesNodes.forEach((x,y)-> result.append(" ").append(y));
            log.error(result.toString());
            return null;
        }
        /* Раскидали листья*/
        while (!rolesLeafs.isEmpty()){
            RolesTree.RoleNode role = rolesLeafs.poll();
            RolesTree.RoleNode parent = rolesNodes.get(role.getParentId());
            parent.getChild().add(role);
        }
        for (Map.Entry<Long, RolesTree.RoleNode> entry : rolesNodes.entrySet()) {
            RolesTree.RoleNode roleNode = entry.getValue();
            if (roleNode.getParentId().equals(startNode.getKey())) {
                startNode.getChildren().add(roleNode);
            } else {
                rolesNodes.get(roleNode.getParentId()).getChildren().add(roleNode);
            }
        }
        return startNode;
    }

    private RolesTree.RoleNode mapRow(ResultSet rs) throws SQLException {
        final RolesTree.RoleNode role = new RolesTree.RoleNode();
        role.setKey(rs.getLong("id"));
        role.setIsCatalog(rs.getBoolean("is_catalog"));
        role.setParentId(rs.getLong("parent_id"));
        role.setLabel(rs.getString("title"));
        role.setStatus(ERoleStatus.valueOf(rs.getString("status")));
        role.setDescription(rs.getString("description"));
        role.setCreatedAt(rs.getTimestamp("createdat").toLocalDateTime());
        role.setUpdatedAt(rs.getTimestamp("updatedat").toLocalDateTime());
        String rawPath =  rs.getString("path");
        if(!rawPath.isEmpty()){
            role.setPath(rawPath.substring(1));
        }
        role.setModifyBy(rs.getString("email"));
        return role;
    }

}
