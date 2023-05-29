package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserEmailRepository {
    int saveVerifiedEmailToken(final Long userId,final String token, LocalDateTime expired);

    int deleteUsedEmailApprovedTokenByUserId(Long id);

    UserAccount getUserAccountByVerifiedTokenAndEmail(String email, String token);

    int updateUserEmailStatusByUserId(Long userId, Boolean status);
}
