package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    int setDefaultRoleForNewUser(Long userId);
}
