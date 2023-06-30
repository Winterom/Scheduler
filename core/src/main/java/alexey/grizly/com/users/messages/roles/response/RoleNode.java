package alexey.grizly.com.users.messages.roles.response;

import alexey.grizly.com.users.models.ERoleStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class RoleNode {
    Collection<Role> roles;
    @Data
    public static class Role implements MessageTreeNode<Role>{
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

        @Override
        public Long getParent() {
            return this.parentId;
        }

        @Override
        public Collection<Role> getChild() {
            return this.children;
        }
    }
}

