package alexey.grizly.com.users.services;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    String authentication(String emailOrPhone, String password, HttpServletResponse response);

    String authenticationByRefreshToken(Cookie cookie);
}
