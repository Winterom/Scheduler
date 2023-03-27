package alexey.grizly.com.users.services;

import alexey.grizly.com.users.dtos.request.AuthRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDetails authentication(AuthRequestDto dto);
}
