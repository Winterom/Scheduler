package alexey.grizly.com.users.service;

import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserAccountDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserAccountDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount =  null;
        return null;
    }
}
