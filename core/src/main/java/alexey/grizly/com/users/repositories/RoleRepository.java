package alexey.grizly.com.users.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    int setRole(Long userId, Long roleId);
}
