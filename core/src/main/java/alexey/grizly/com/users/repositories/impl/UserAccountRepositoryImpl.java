package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountSimpleRowMapper;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final String SELECT_SIMPLE_USER="SELECT * FROM users WHERE email=:email;";
    private final String CREATE_NEW_USER="INSERT INTO users (email, phone, password, credential_expired, e_status, createdat) "+
            "VALUES (:email,:phone,:password,:credentialExpired, :status, :createdAt);";
    private final String COUNT_USAGE_EMAIL="SELECT COUNT(u.email) FROM users as u where u.email = :email;";
    private final String UPDATE_PASSWORD = "UPDATE users  set credential_expired =:credentialExpired," +
            "password = :password where id=:userId";
    private final String COUNT_USAGE_PHONE ="SELECT COUNT(u.phone) FROM users as u where u.phone = :phone;";

    private final String SET_EMAIL_VERIFIED="UPDATE users set is_email_verified=:status where id=:id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public UserAccount getSimpleUserAccount(final String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        try {
            return jdbcTemplate.queryForObject(SELECT_SIMPLE_USER,namedParameters, new UserAccountSimpleRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    @Transactional
    public int updatePassword(Long userId, String passwordHash, LocalDateTime credentialExpired) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId",userId)
                .addValue("password",passwordHash)
                .addValue("credentialExpired",credentialExpired);
        return jdbcTemplate.update(UPDATE_PASSWORD,namedParameters);
    }

    @Override
    @Nullable
    @Transactional
    public Long registrationNewUser(final String email,
                                   final String phone,
                                   final String passwordHash,
                                   final LocalDateTime credentialExpired,
                                   final EUserStatus status,
                                   final LocalDateTime createdAt) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email.toLowerCase())
                .addValue("phone", phone)
                .addValue("password", passwordHash)
                .addValue("credentialExpired",credentialExpired)
                .addValue("status",status.toString())
                .addValue("createdAt",createdAt);
        jdbcTemplate.update(CREATE_NEW_USER, namedParameters,keyHolder,new String[] { "id" });
        if(keyHolder.getKey()==null){
            return null;
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long countOfUsageEmail(@NonNls String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate
                .queryForObject(COUNT_USAGE_EMAIL,
                        namedParameters,Long.class);
    }

    @Override
    public Long countOfUsagePhone(String phone) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("phone",phone);
        return jdbcTemplate
                .queryForObject(COUNT_USAGE_PHONE,
                        namedParameters,Long.class);
    }

    @Override
    @Transactional
    public int setEmailVerifiedStatusByUserId(Long id, Boolean status) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("status",status);
        return jdbcTemplate
                .update(SET_EMAIL_VERIFIED, namedParameters);
    }
}
