package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public class AuthRepositoryImpl implements AuthRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AuthRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveRefreshToken(final Long id, final Date expired, final String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("expired", expired)
                .addValue("token", token);
        return jdbcTemplate.update("INSERT INTO refresh_tokens (id,expired,token) values (:id,:expired,:token) " +
                "ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token", namedParameters);
    }
}
