package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

@Service
public interface UserEmailService {
    boolean approvedUserEmail(final String email, final String token);

    String generateVerifiedEmailToken(final Long userId);
}
