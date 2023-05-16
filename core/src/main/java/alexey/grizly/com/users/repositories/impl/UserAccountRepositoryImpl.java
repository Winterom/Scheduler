package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount getSimpleUserAccount(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=:email",namedParameters,UserAccount.class);
    }
    @Override
    @Transactional
    public int saveRestorePasswordToken(final Long userId, final LocalDateTime expireDate, final String token){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("expire", expireDate)
                .addValue("token", token);
        return jdbcTemplate.update("INSERT INTO restore_psw_token(id, token, expire) VALUES(:id,:token,:expire) " +
                "ON CONFLICT (id) DO UPDATE SET token=:token, expire=:expire",namedParameters);
    }
}
