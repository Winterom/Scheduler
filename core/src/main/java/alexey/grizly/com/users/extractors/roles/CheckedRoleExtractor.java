package alexey.grizly.com.users.extractors.roles;

import alexey.grizly.com.users.models.ERoleStatus;
import alexey.grizly.com.users.models.roles.CheckedRoleForDelete;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CheckedRoleExtractor implements ResultSetExtractor<CheckedRoleForDelete> {
    private final CheckedRoleForDelete checkedRole =  new CheckedRoleForDelete();
    private final Map<Long, CheckedRoleForDelete.CheckedRole>  roleMap = new HashMap<>();
    private Integer countCatalogs=0;
    private Integer countRoles = 0;
    @Override
    public CheckedRoleForDelete extractData(ResultSet rs) throws SQLException, DataAccessException {
       while (rs.next()){
           Long roleId = rs.getLong("id");
           CheckedRoleForDelete.CheckedRole role = roleMap.get(roleId);
           if(role==null){
               role = mapRow(rs);
               roleMap.put(role.getKey(),role);
           }
           if(role.getIsCatalog()){
               this.countCatalogs = this.countCatalogs+1;
           }else {
               this.countRoles = this.countRoles+1;
           }
           long userId = rs.getLong("user_id");
           if(userId==0){
               continue;
           }
           CheckedRoleForDelete.User user = new CheckedRoleForDelete.User();
           user.setKey(userId);
           user.setEmail(rs.getString("email"));
           role.getUsers().add(user);
       }
       this.checkedRole.setRoleCount(this.countRoles);
       this.checkedRole.setCatalogCount(this.countCatalogs);
       this.checkedRole.setRoleAssignedUsers(roleMap.values());
       return this.checkedRole;
    }

    public CheckedRoleForDelete.CheckedRole mapRow(ResultSet rs) throws SQLException {
        final CheckedRoleForDelete.CheckedRole role = new CheckedRoleForDelete.CheckedRole();
        role.setKey(rs.getLong("id"));
        role.setIsCatalog(rs.getBoolean("is_catalog"));
        role.setParentId(rs.getLong("parent_id"));
        role.setLabel(rs.getString("title"));
        role.setStatus(ERoleStatus.valueOf(rs.getString("status")));
        String rawPath =  rs.getString("path");
        if(!rawPath.isEmpty()){
            role.setPath(rawPath.substring(1));
        }
        return role;
    }


}
