package alexey.grizly.com.users.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Repository
public interface UserPasswordRepository {

    int updatePassword(final Long userId,
                       final String passwordHash,
                       final LocalDateTime credentialExpired,
                       final LocalDateTime updatedAt);


    int updatePassword(final String email,
                       final String passwordHash,
                       final LocalDateTime credentialExpired,
                       final LocalDateTime updatedAt);

    String getPasswordByEmail(final String email);
}
