package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository {
    int setRole(final Long userId, final Long roleId);
    RolesTree.RoleNode getAllRolesTree();
    List<AuthoritiesNode.Authority> getAuthoritiesByRoleId(final Long roleId);
    List<AuthoritiesNode.Authority> getAllAuthorities();
}
