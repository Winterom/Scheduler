package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.dtos.response.UserProfileResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository {
    UserProfileResponse getUserAccountWithRoles(String email);
}
