package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.repositories.UserPhoneNumberRepository;
import alexey.grizly.com.users.services.UserPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPhoneNumberServiceImpl implements UserPhoneNumberService {
    private final UserPhoneNumberRepository userPhoneNumberRepository;

    @Autowired
    public UserPhoneNumberServiceImpl(UserPhoneNumberRepository userPhoneNumberRepository) {
        this.userPhoneNumberRepository = userPhoneNumberRepository;
    }

    @Override
    public boolean phoneBusyCheck(final String phone) {
        return userPhoneNumberRepository.countOfUsagePhone(phone)>0;
    }
}
