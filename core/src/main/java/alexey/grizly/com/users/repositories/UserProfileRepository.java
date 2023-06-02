package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.dtos.response.UserProfileResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository {
    UserProfileResponseDto getUserAccountWithRoles(String email);
}
