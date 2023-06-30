package alexey.grizly.com.users.services;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.AllRolesMessage;
import alexey.grizly.com.users.messages.roles.response.RoleNode;
import org.springframework.stereotype.Service;


@Service
public interface RolesAndAuthoritiesService {
    ResponseMessage<RoleNode> getAllRoles(final AllRolesMessage message);
    ResponseMessage<RoleNode> getAllAuthorities(final AllRolesMessage message);
}
