package alexey.grizly.com.users.models;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class AppRole {
    private Long id;
    private String title;
    private String description;
    private Set<UsersRoles> usersRoles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
