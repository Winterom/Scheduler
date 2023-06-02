package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.dtos.response.UserProfileResponseDto;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import alexey.grizly.com.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    public final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfileResponseDto getProfileByEmail(String email) {
        return userProfileRepository.getUserAccountWithRoles(email);
    }
}
