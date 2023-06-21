package alexey.grizly.com.users.messages.roles.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleByGroups {
    private Long id;
    private Boolean isCatalog;
    private Long parentId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String modifyBy; //email редактора
    private List<RoleByGroups> roles;


}

