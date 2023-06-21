package alexey.grizly.com.users.services;

import alexey.grizly.com.commons.helpers.Paging;
import alexey.grizly.com.users.messages.roles.request.AllRolesAndAuthoritiesMessage;
import alexey.grizly.com.users.messages.roles.response.RolesByGroups;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolesAndAuthoritiesService {
    List<RolesByGroups> getAllRolesAndAuthorities(final AllRolesAndAuthoritiesMessage message);
}
