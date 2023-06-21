package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.AllRolesMessage;
import alexey.grizly.com.users.messages.roles.response.RoleByGroups;
import alexey.grizly.com.users.repositories.RoleRepository;
import alexey.grizly.com.users.services.RoleService;
import alexey.grizly.com.users.services.RolesAndAuthoritiesService;
import alexey.grizly.com.users.ws_handlers.ERolesAuthoritiesWSEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolesAndAuthoritiesServiceImpl implements RolesAndAuthoritiesService, RoleService {
    private final RoleRepository roleRepository;
    private static final  Long ROLE_ID_FOR_NEW_USER=6L;

    @Autowired
    public RolesAndAuthoritiesServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public int setRole(final Long userId, final Long roleId) {
        return roleRepository.setRole(userId,roleId);
    }

    @Override
    public int setDefaultRoleForNewUser(final Long userId) {
        return setRole(userId, ROLE_ID_FOR_NEW_USER);
    }

    @Override
    public ResponseMessage<Collection<RoleByGroups>> getAllRoles(AllRolesMessage message) {
        List<RoleByGroups> rawList = this.roleRepository.getAllRoles();
        Map<Long,RoleByGroups> resultMap = new HashMap<>();
        rawList.forEach(x->{
            if (Boolean.TRUE.equals(x.getIsCatalog())){
                resultMap.put(x.getId(),x);
                x.setRoles(new ArrayList<>());
            }
        });
        rawList.forEach(x->{
            if(Boolean.FALSE.equals(x.getIsCatalog())){
                resultMap.get(x.getParentId())
                        .getRoles()
                        .add(x);
            }
        });
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.ALL_ROLES.name()
                , resultMap.values()
                , ResponseMessage.ResponseStatus.OK);
    }
}
