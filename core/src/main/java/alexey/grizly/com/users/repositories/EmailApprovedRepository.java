package alexey.grizly.com.users.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface EmailApprovedRepository {
    int saveVerifiedEmailToken(final Long userId,final String token);
    int checkVerifiedEmailToken(final Long userId, final String token);
}
