package alexey.grizly.com.users.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDetails authentication(String emailOrPhone, String password);
}
