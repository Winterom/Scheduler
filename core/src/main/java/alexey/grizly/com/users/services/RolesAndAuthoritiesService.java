package alexey.grizly.com.users.services;

import alexey.grizly.com.users.messages.profile.response.ResponseMessage;
import alexey.grizly.com.users.messages.roles.request.AllRolesMessage;
import alexey.grizly.com.users.messages.roles.response.RoleByGroups;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public interface RolesAndAuthoritiesService {
    ResponseMessage<RoleByGroups> getAllRoles(final AllRolesMessage message);
}
