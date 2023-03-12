package alexey.grizly.com.users.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Table(name = "users")
public class UserAccount {
    @Id
    private Long id;
    @Column(value = "email")
    private String email;
    @MappedCollection(idColumn = "user_id",keyColumn = "user_id")
    private Set<UsersRoles> usersRoles;
    @Column(value = "createdAt")
    private LocalDateTime createdAt;
    @Column(value = "updatedAt")
    private LocalDateTime updatedAt;
}
