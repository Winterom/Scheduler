package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.repositories.RoleRepository;
import alexey.grizly.com.users.services.RoleAndAuthoritiesService;
import alexey.grizly.com.users.services.RoleForUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleAndAuthoritiesServiceImpl implements RoleAndAuthoritiesService, RoleForUserService {
    private final RoleRepository roleRepository;
    private final static Long ROLE_ID_FOR_NEW_USER=1L;

    @Autowired
    public RoleAndAuthoritiesServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public int setRole(final Long userId, final Long roleId) {
        return roleRepository.setRole(userId,roleId);
    }

    @Override
    public int setDefaultRoleForNewUser(final Long userId) {
        return setRole(userId,ROLE_ID_FOR_NEW_USER);
    }
}
