package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.auth.UserAccountWithAuthoritiesExtractor;
import alexey.grizly.com.users.models.user.UserAccount;
import alexey.grizly.com.users.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
@SuppressWarnings("FieldCanBeLocal")
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private static final String SAVE_REFRESH_TOKEN="INSERT INTO refresh_tokens (id,expired,token) VALUES (:id,:expired,:token) "+
            "ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token";
    private static final String GET_USER_BY_REFRESH_TOKEN="SELECT u.id,u.email,u.e_status,u.credential_expired, u.password, a.e_authorities " +
            "FROM refresh_tokens rf" +
            " LEFT JOIN users u on u.id = rf.id" +
            " LEFT JOIN users_roles ur on u.id = ur.user_id" +
            " LEFT JOIN roles r on ur.role_id = r.id" +
            " LEFT JOIN roles_authorities ra on r.id = ra.role_id" +
            " LEFT JOIN authorities a on ra.authority_id = a.id" +
            "   where rf.token=:token and rf.expired>CURRENT_TIMESTAMP" +
            "   and(u.e_status='ACTIVE' or u.e_status='NEW_USER')";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RefreshTokenRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRefreshToken(final Long id, final Date expired, final String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("expired", expired)
                .addValue("token", token);
        jdbcTemplate.update(SAVE_REFRESH_TOKEN
                , namedParameters);
    }

    @Override
    public UserAccount checkRefreshToken(String refreshToken) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("token",refreshToken);
        return jdbcTemplate.query(GET_USER_BY_REFRESH_TOKEN,
                namedParameters,
                new UserAccountWithAuthoritiesExtractor());
    }
}
