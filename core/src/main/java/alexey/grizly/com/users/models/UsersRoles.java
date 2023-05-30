package alexey.grizly.com.users.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class UsersRoles implements Serializable {
    private Long roleId;
    private Long userId;

}
