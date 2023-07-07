package alexey.grizly.com.users.messages.roles.request;

import lombok.Data;

@Data
public class DragDropRoleMessage {
    private Long newParentId;
    private Long roleId;
}
