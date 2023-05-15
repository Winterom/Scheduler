package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.services.ChangePasswordTokenService;
import alexey.grizly.com.users.services.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ChangePasswordTokenService changePasswordTokenService;
    private final SecurityProperties securityProperties;

    @Autowired
    public UserPasswordServiceImpl(final NamedParameterJdbcTemplate jdbcTemplate,
                                   final ChangePasswordTokenService changePasswordTokenService,
                                   final SecurityProperties securityProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.changePasswordTokenService = changePasswordTokenService;
        this.securityProperties = securityProperties;
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
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = this.securityProperties.getRestorePasswordTokenLength();
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }
}
