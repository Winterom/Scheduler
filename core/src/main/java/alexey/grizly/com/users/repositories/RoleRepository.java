package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.messages.roles.response.RoleByGroups;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository {
    int setRole(Long userId, Long roleId);

    List<RoleByGroups> getAllRoles();
}
