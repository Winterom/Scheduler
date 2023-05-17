package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final static String SELECT_SIMPLE_USER="SELECT * FROM users WHERE email=:email";
    private final static String SAVE_RESTORE_PASSWORD_TOKEN="INSERT INTO restore_psw_token(id, token, expire) VALUE(:id,:token,:expire) "+
            "ON CONFLICT (id) DO UPDATE SET token=:token, expire=:expire";
    private final static String CREATE_NEW_USER="INSERT INTO users (email, phone, password, credential_expired, e_status, createdat) "+
            "VALUE (:email,:phone,:password,:credentialExpired,:status,:createdAt)";
    
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount getSimpleUserAccount(final String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.queryForObject(SELECT_SIMPLE_USER,namedParameters,UserAccount.class);
    }
    @Override
    @Transactional
    public int saveRestorePasswordToken(final Long userId, final LocalDateTime expireDate, final String token){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("expire", expireDate)
                .addValue("token", token);
        return jdbcTemplate.update(SAVE_RESTORE_PASSWORD_TOKEN, namedParameters);
    }

    @Override
    @Nullable
    public Long registrationNewUser(final String email,
                                   final String phone,
                                   final String passwordHash,
                                   final LocalDateTime credentialExpired,
                                   final EUserStatus status,
                                   final LocalDateTime createdAt) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("phone", phone)
                .addValue("password", passwordHash)
                .addValue("credentialExpired",credentialExpired)
                .addValue("status",status.toString())
                .addValue("createdAt",createdAt);
        jdbcTemplate.update(CREATE_NEW_USER, namedParameters,keyHolder);
        return keyHolder.getKey().longValue();
    }
}
