package alexey.grizly.com.users.services;


import org.springframework.stereotype.Service;



@Service
public interface UserRegistrationService {
    void createNewUserAccount(final String email,
                              final String phone,
                              final String password,
                              final String name,
                              final String surname,
                              final String lastname);
}
