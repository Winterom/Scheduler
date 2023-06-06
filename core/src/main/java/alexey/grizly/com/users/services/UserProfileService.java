package alexey.grizly.com.users.services;

import alexey.grizly.com.users.dtos.response.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    UserProfileResponse getProfileByEmail(final String email);
}
