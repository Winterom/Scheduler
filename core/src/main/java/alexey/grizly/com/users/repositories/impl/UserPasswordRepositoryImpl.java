package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.profile.UserPasswordExtractor;
import alexey.grizly.com.users.repositories.UserPasswordRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserPasswordRepositoryImpl implements UserPasswordRepository {
    private static final String UPDATE_PASSWORD_BY_ID = "UPDATE users  set credential_expired =:credentialExpired," +
            "password = :password, updatedat=:updatedAt where id=:userId";
    private static final String UPDATE_PASSWORD_BY_EMAIL = "UPDATE users  set credential_expired =:credentialExpired," +
            "password = :password, updatedat=:updatedAt where email=:email";
    private static final String GET_PASSWORD_BY_EMAIL = "SELECT u.password FROM users as u WHERE u.email=:email";


    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserPasswordRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public int updatePassword(final Long userId,
                              final String passwordHash,
                              final LocalDateTime credentialExpired,
                              final LocalDateTime updatedAt) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId",userId)
                .addValue("password",passwordHash)
                .addValue("credentialExpired",credentialExpired);
        return jdbcTemplate.update(UPDATE_PASSWORD_BY_ID,namedParameters);
    }
    @Override
    @Transactional
    public int updatePassword(final String email,
                              final String passwordHash,
                              final LocalDateTime credentialExpired,
                              final LocalDateTime updatedAt) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email)
                .addValue("password",passwordHash)
                .addValue("credentialExpired",credentialExpired)
                .addValue("updatedAt",updatedAt);
        return jdbcTemplate.update(UPDATE_PASSWORD_BY_EMAIL,namedParameters);
    }

    @Override
    @Nullable
    public String getPasswordByEmail(final String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate.query(GET_PASSWORD_BY_EMAIL,namedParameters,new UserPasswordExtractor());
    }

}
