package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEmailService {
    List<String> approvedUserEmail(final String email, final String token);
    List<String> generateVerifiedEmailToken(final Long userId);
    boolean emailBusyCheck(final String email);
}
