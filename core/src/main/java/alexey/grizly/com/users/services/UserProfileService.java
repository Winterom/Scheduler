package alexey.grizly.com.users.services;

import alexey.grizly.com.users.dtos.response.UserProfileResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    UserProfileResponseDto getProfileByEmail(final String email);
}
