package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

@Service
public interface RoleAndAuthoritiesService {
    int setRole(Long userId, Long roleId);
}
