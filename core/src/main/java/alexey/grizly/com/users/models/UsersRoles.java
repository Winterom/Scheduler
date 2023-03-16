package alexey.grizly.com.users.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;



@Getter
@Setter
@Table("users_roles")
public class UsersRoles {
    @Column("role_id")
    private Long roleId;


}
