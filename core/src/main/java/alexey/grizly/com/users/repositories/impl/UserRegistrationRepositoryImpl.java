package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.repositories.UserRegistrationRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserRegistrationRepositoryImpl implements UserRegistrationRepository {
    private final String CREATE_NEW_USER="INSERT INTO users (email, phone, password, credential_expired, e_status, createdat) "+
            "VALUES (:email,:phone,:password,:credentialExpired, :status, :createdAt);";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRegistrationRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    @Transactional
    public Long saveNewUser(final String email,
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
        Number id = keyHolder.getKey();
        if(id!=null){
            return  id.longValue();
        }else {
            return null;
        }
    }
}
