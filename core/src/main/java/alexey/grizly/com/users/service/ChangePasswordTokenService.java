package alexey.grizly.com.users.service;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordTokenService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ChangePasswordTokenService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveToken(UserAccount userAccount,String token){
        return true;
    }
}
