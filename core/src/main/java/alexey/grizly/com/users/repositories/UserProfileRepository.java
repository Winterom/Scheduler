package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.models.user.UserProfileWithRolesAndTokens;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserProfileRepository {
    UserProfileWithRolesAndTokens getUserAccountWithRoles(final String email);
    int updateUserProfile(final Long id,
                          final String email,
                          final String phone,
                          final LocalDateTime updateAt);
    int updateEmail(final Long id,
                    final String email,
                    final LocalDateTime updateAt);
    int updatePhone(final Long id,
                    final String phone,
                    final LocalDateTime updateAt);
}
