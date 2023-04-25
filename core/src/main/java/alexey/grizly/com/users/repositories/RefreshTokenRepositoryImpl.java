package alexey.grizly.com.users.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RefreshTokenRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveRefreshToken(Long id, Date expired, String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("expired",expired)
                .addValue("token",token);
        return jdbcTemplate.update("INSERT INTO refresh_tokens (id,expired,token) values (:id,:expired,:token) " +
                "ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token",namedParameters);
    }
}
