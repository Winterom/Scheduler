package alexey.grizly.com.users.services;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.DragDropRoleMessage;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNodeResponseMessage;

import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.roles.CheckedRoleForDelete;
import org.springframework.stereotype.Service;


@Service
public interface RolesAndAuthoritiesService {
    ResponseMessage<RolesTree> getAllRoles();
    ResponseMessage<AuthoritiesNodeResponseMessage> getAllAuthorities();
    ResponseMessage<AuthoritiesNodeResponseMessage> getAuthoritiesByRoleId(final Long roleId);
    ResponseMessage<RolesTree> updateRoleAfterDragDrop(DragDropRoleMessage message);
    ResponseMessage<CheckedRoleForDelete> checkRoleForDelete(Long roleId);
}
