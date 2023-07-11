package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.DragDropRoleMessage;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNodeResponseMessage;

import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.roles.CheckedRoleForDelete;
import alexey.grizly.com.users.repositories.RoleRepository;
import alexey.grizly.com.users.services.RoleService;
import alexey.grizly.com.users.services.RolesAndAuthoritiesService;
import alexey.grizly.com.users.ws_handlers.ERolesAuthoritiesWSEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
        RolesTree.RoleNode rolesTree = this.roleRepository.getRolesTree(1L);
        RolesTree roles = new RolesTree();
        roles.setRoles(rolesTree.getChild());
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.ALL_ROLES.name(),
                roles,
                ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<AuthoritiesNodeResponseMessage> getAllAuthorities() {
        List<AuthoritiesNodeResponseMessage.Authority> authorities = roleRepository.getAllAuthorities();
        AuthoritiesNodeResponseMessage authoritiesNode = new AuthoritiesNodeResponseMessage();
        authoritiesNode.setAuthorities(authorities);
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.ALL_AUTHORITIES.name(),
                authoritiesNode,
                ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<AuthoritiesNodeResponseMessage> getAuthoritiesByRoleId(Long roleId) {
        List<AuthoritiesNodeResponseMessage.Authority> authorities = roleRepository.getAuthoritiesByRoleId(roleId);
        AuthoritiesNodeResponseMessage authoritiesNode = new AuthoritiesNodeResponseMessage();
        authoritiesNode.setAuthorities(authorities);
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.AUTHORITIES_BY_ROLE_ID.name(),
                authoritiesNode,
                ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<RolesTree> updateRoleAfterDragDrop(DragDropRoleMessage message) {
        int resultSave = roleRepository.updateRoleAfterDragDrop(message.getRoleId(),message.getNewParentId());
        ResponseMessage<RolesTree> responseMessage = getAllRoles();
        if(resultSave!=1){
           responseMessage.getPayload().setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
            responseMessage.getPayload().setErrorMessages(List.of("Не удалось обновить роль"));
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage<CheckedRoleForDelete> checkRoleForDelete(Long roleId) {
        CheckedRoleForDelete checkedRoleForDelete = roleRepository.getRolesWithAssignedUsers(roleId);
        checkedRoleForDelete.getRoleAssignedUsers().removeIf(CheckedRoleForDelete.CheckedRole::getIsCatalog);
        return new ResponseMessage<>(ERolesAuthoritiesWSEvents.CHECK_ROLE_FOR_DELETE.name(),
                checkedRoleForDelete,
                ResponseMessage.ResponseStatus.OK);
    }

}
