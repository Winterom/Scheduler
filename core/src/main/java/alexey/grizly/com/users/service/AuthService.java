package alexey.grizly.com.users.service;

import alexey.grizly.com.users.dto.request.AuthRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDetails authentication(AuthRequestDto dto);
}
