package alexey.grizly.com.users.services;

import org.springframework.stereotype.Service;

@Service
public interface UserPhoneNumberService {
    boolean phoneBusyCheck(final String phone);
}
