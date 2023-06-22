package alexey.grizly.com.users.messages.roles.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class RoleByGroups {
    Collection<Role> roles;
    @Data
    public static class Role{
        private Long id;
        private Boolean isCatalog;
        private Long parentId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String modifyBy; //email редактора
        private List<Role> roles;
    }
}

