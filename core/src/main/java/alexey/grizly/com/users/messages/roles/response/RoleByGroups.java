package alexey.grizly.com.users.messages.roles.response;

import alexey.grizly.com.users.models.ERoleStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class RoleByGroups {
    Collection<Role> roles;
    @Data
    public static class Role{
        private Long key;
        private Boolean isCatalog;
        private Long parentId;
        private String label;
        private String description;
        private ERoleStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String modifyBy; //email редактора
        private List<Role> children;
    }
}

