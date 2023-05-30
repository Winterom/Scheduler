package alexey.grizly.com.users.repositories;


import alexey.grizly.com.users.models.UserAccountWithEmailApprovedToken;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserEmailRepository {
    int saveVerifiedEmailToken(final Long userId,final String token, LocalDateTime expired);

    int deleteUsedEmailApprovedTokenByUserId(Long id);

    UserAccountWithEmailApprovedToken getUserAccountWithVerifiedToken(String email);

    UserAccountWithEmailApprovedToken getUserAccountWithVerifiedToken(Long userId);

    int updateUserEmailStatusByUserId(Long userId, Boolean status);

    Long countOfUsageEmail(String email);
}
