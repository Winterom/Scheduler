package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
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
    public ResponseMessage<RolesTree> getAllRoles() {
        List<RolesTree.RoleNode> rawList = this.roleRepository.getAllRoles();
        Map<Long, RolesTree.RoleNode> resultMap = new HashMap<>();
        rawList.forEach(x->{
            if (Boolean.TRUE.equals(x.getIsCatalog())){
                resultMap.put(x.getKey(),x);
                x.setChildren(new ArrayList<>());
            }
        });
        rawList.forEach(x->{
            if(Boolean.FALSE.equals(x.getIsCatalog())){
                resultMap.get(x.getParentId())
                        .getChildren()
                        .add(x);
            }
        });
        RolesTree roles = new RolesTree();
        roles.setRoles(resultMap.values());
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.ALL_ROLES.name(),
                roles,
                ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<AuthoritiesNode> getAllAuthorities() {
        List<AuthoritiesNode.Authority> authorities = roleRepository.getAllAuthorities();
        AuthoritiesNode authoritiesNode = new AuthoritiesNode();
        authoritiesNode.setAuthorities(authorities);
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.ALL_AUTHORITIES.name(),
                authoritiesNode,
                ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<AuthoritiesNode> getAuthoritiesByRoleId(Long roleId) {
        List<AuthoritiesNode.Authority> authorities = roleRepository.getAuthoritiesByRoleId(roleId);
        AuthoritiesNode authoritiesNode = new AuthoritiesNode();
        authoritiesNode.setAuthorities(authorities);
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.AUTHORITIES_BY_ROLE_ID.name(),
                authoritiesNode,
                ResponseMessage.ResponseStatus.OK);
    }
}
