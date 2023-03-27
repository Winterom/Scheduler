package alexey.grizly.com.users.service;


import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ChangePasswordTokenService changePasswordTokenService;

    @Autowired
    public UserAccountService(UserRepository userRepository, NamedParameterJdbcTemplate jdbcTemplate, ChangePasswordTokenService changePasswordTokenService) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.changePasswordTokenService = changePasswordTokenService;
    }

    public String generateAndSaveRestorePasswordToken(UserAccount userAccount){
        String token = generateRestorePasswordToken();
        if (changePasswordTokenService.saveToken(userAccount,token)){
            return token;
        }
        return null;
    }

    public UserAccount getSimpleUserAccount(String email){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=:email",namedParameters,UserAccount.class);
    }

    private String generateRestorePasswordToken(){
        return UUID.randomUUID().toString();
    }
}
