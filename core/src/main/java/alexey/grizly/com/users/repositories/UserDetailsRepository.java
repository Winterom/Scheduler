package alexey.grizly.com.users.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository {
    UserDetails loadByEmail(String email);
    UserDetails loadByPhone(String phone);
}
