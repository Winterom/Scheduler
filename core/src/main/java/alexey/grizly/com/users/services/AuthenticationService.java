package alexey.grizly.com.users.services;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    String authentication(final String emailOrPhone,
                          final String password,
                          final HttpServletResponse response);

    String authenticationByRefreshToken(final Cookie cookie);
}
