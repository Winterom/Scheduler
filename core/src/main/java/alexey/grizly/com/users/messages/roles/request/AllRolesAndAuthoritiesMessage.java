package alexey.grizly.com.users.messages.roles.request;

import lombok.Data;

@Data
public class AllRolesAndAuthoritiesMessage {
    private Integer pageSize;
    private Integer currentPage;
}
