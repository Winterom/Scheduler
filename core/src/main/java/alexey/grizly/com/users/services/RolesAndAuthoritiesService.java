package alexey.grizly.com.users.services;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.DragDropRoleMessage;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
import org.springframework.stereotype.Service;


@Service
public interface RolesAndAuthoritiesService {
    ResponseMessage<RolesTree> getAllRoles();
    ResponseMessage<AuthoritiesNode> getAllAuthorities();
    ResponseMessage<AuthoritiesNode> getAuthoritiesByRoleId(final Long roleId);

    ResponseMessage<RolesTree> updateRoleAfterDragDrop(DragDropRoleMessage message);
}
