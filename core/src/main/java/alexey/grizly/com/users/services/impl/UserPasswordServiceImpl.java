package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.ChangePasswordTokenService;
import alexey.grizly.com.users.services.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ChangePasswordTokenService changePasswordTokenService;

    @Autowired
    public UserPasswordServiceImpl( NamedParameterJdbcTemplate jdbcTemplate, ChangePasswordTokenService changePasswordTokenService) {
        this.jdbcTemplate = jdbcTemplate;
        this.changePasswordTokenService = changePasswordTokenService;
    }

    @Override
    public String generateAndSaveRestorePasswordToken(UserAccount userAccount){
        String token = generateRestorePasswordToken();
        if (changePasswordTokenService.saveToken(userAccount,token)){
            return token;
        }
        return null;
    }

    @Override
    public UserAccount getSimpleUserAccount(String email){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=:email",namedParameters,UserAccount.class);
    }

    @Override
    public boolean updatePassword(String email, String passwordHash, String token){
        return true;
    }
    private String generateRestorePasswordToken(){
        return UUID.randomUUID().toString();
    }
}
