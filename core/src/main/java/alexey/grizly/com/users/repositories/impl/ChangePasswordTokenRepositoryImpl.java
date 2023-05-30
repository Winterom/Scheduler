package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountWithPasswordChangeTokenExtractor;
import alexey.grizly.com.users.models.UserAccountWithPasswordChangeToken;
import alexey.grizly.com.users.repositories.ChangePasswordTokenRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class ChangePasswordTokenRepositoryImpl implements ChangePasswordTokenRepository {
    private static final String SAVE_CHANGE_PASSWORD_TOKEN ="INSERT INTO change_psw_token(id, token, expired) VALUES (:id,:token,:expired) "+
            "ON CONFLICT (id) DO UPDATE SET token = :token, expired = :expired;";
    private static final String CHECK_TOKEN = "SELECT u.id,u.e_status, u.password, rpt.token , rpt.expired as token_expired, rpt.createdat as token_created " +
            " FROM users as u left join change_psw_token rpt on u.id = rpt.id" +
            " where u.email=:email and rpt.token and (CURRENT_TIMESTAMP < rpt.expired)";
    private static final String DELETE_USED_TOKEN = "DELETE FROM change_psw_token as rpt WHERE rpt.id=:id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ChangePasswordTokenRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int saveChangePasswordToken(final Long userId, final LocalDateTime expireDate, final String token){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("expired", expireDate)
                .addValue("token", token);
        return jdbcTemplate.update(SAVE_CHANGE_PASSWORD_TOKEN, namedParameters);
    }

    @Override
    @Nullable
    public UserAccountWithPasswordChangeToken isUserAccountHaveToken(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate.query(CHECK_TOKEN,namedParameters,new UserAccountWithPasswordChangeTokenExtractor());
    }

    @Override
    public void deleteUsedToken(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(DELETE_USED_TOKEN,namedParameters);
    }
}
