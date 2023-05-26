package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailApprovedRepository {
    int saveVerifiedEmailToken(final Long userId,final String token);

    int deleteUsedEmailApprovedTokenByUserId(Long id);

    UserAccount getUserAccountByVerifiedTokenAndEmail(String email, String token);
}
