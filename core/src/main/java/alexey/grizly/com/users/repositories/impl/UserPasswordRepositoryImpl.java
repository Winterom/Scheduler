package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.repositories.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserPasswordRepositoryImpl implements UserPasswordRepository {
    private static final String UPDATE_PASSWORD = "UPDATE users  set credential_expired =:credentialExpired," +
            "password = :password where id=:userId";


    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserPasswordRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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


}
