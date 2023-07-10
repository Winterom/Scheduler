package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.messages.roles.response.AuthoritiesNodeResponseMessage;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.roles.CheckedRoleForDelete;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository {
    int setRole(final Long userId, final Long roleId);
    RolesTree.RoleNode getRolesTree(final Long roleId);
    CheckedRoleForDelete getRolesWithAssignedUsers(final Long roleId);
    List<AuthoritiesNodeResponseMessage.Authority> getAuthoritiesByRoleId(final Long roleId);
    List<AuthoritiesNodeResponseMessage.Authority> getAllAuthorities();

    int updateRoleAfterDragDrop(final Long roleId, final Long newParentId);
}
