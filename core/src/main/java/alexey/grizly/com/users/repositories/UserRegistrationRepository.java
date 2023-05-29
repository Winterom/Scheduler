package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.EUserStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRegistrationRepository {
    Long saveNewUser(final String email,
                     final String phone,
                     final String passwordHash,
                     final LocalDateTime credentialExpired,
                     final EUserStatus status,
                     final LocalDateTime createdAt);
}
