package alexey.grizly.com.users.messages.roles.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RolesByGroups {
    private Long id;
    private Boolean isCatalog;
    private Long parentId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String modifyBy; //email редактора


}

