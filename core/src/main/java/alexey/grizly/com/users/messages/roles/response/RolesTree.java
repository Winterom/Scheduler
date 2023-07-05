package alexey.grizly.com.users.messages.roles.response;

import alexey.grizly.com.users.models.ERoleStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class RolesTree {
    Collection<RoleNode> roles;
    @Data
    public static class RoleNode implements MessageTreeNode<RoleNode>{
        private Long key;
        private Boolean isCatalog;
        private Long parentId;
        private String label;
        private String description;
        private ERoleStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String path;
        private String modifyBy; //email редактора
        private List<RoleNode> children;

        @Override
        public Long getParent() {
            return this.parentId;
        }

        @Override
        public Collection<RoleNode> getChild() {
            return this.children;
        }
    }
}

