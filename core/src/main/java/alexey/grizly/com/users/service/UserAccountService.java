package alexey.grizly.com.users.service;


import alexey.grizly.com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;



@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountService(UserRepository userRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

}
