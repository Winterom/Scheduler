package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.messages.roles.request.AllRolesAndAuthoritiesMessage;
import alexey.grizly.com.users.messages.roles.response.RolesByGroups;
import alexey.grizly.com.users.repositories.RoleRepository;
import alexey.grizly.com.users.services.RolesAndAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolesAndAuthoritiesServiceImpl implements RolesAndAuthoritiesService {
    private final RoleRepository roleRepository;
    @Autowired
    public RolesAndAuthoritiesServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RolesByGroups> getAllRolesAndAuthorities(AllRolesAndAuthoritiesMessage message) {
        return this.roleRepository.getAllRoles();
    }
}
