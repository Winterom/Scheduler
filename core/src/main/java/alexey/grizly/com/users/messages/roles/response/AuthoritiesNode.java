package alexey.grizly.com.users.messages.roles.response;



import lombok.Data;

import java.util.Collection;
import java.util.List;

public class AuthoritiesNode {
    Collection<Authority> authorities;

    @Data
    public static class Authority implements MessageTreeNode<Authority>{
        private Long key;
        private Boolean isCatalog;
        private Long parentId;
        private String title;
        private String description;
        private List<Authority> children;

        @Override
        public Long getParent() {
            return this.parentId;
        }

        @Override
        public Collection<Authority> getChild() {
            return this.children;
        }
    }
}
