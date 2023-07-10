package alexey.grizly.com.users.messages.roles.response;



import lombok.Data;

import java.util.Collection;
import java.util.List;
@Data
public class AuthoritiesNodeResponseMessage {
    Collection<Authority> authorities;

    @Data
    public static class Authority {
        private Long key;
        private Boolean isCatalog;
        private Long parentId;
        private String label;
        private String description;
        private List<Authority> children;
    }
}
