package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

@Service
public interface RoleForUserService {
    int setDefaultRoleForNewUser(Long userId);
}
