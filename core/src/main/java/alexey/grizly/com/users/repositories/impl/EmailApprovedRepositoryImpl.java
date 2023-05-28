package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountSimpleRowMapper;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.EmailApprovedRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class EmailApprovedRepositoryImpl implements EmailApprovedRepository {
    private final String DELETE_USED_TOKEN = "DELETE FROM email_approved_token as eat WHERE eat.userid=:id";
    private final String INSERT_TOKEN="INSERT INTO email_approved_token (userid, token) VALUES (:id,:token) " +
            "ON CONFLICT (userId) DO UPDATE SET token=:token";

    private final String VERIFIED_TOKEN = "SELECT * FROM users as u left join email_approved_token eat on u.id = eat.userid" +
            " where eat.token=:token and u.email=:email";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmailApprovedRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVerifiedEmailToken(final Long userId, final String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("token",token);
        return jdbcTemplate.update(INSERT_TOKEN,namedParameters);
    }

    @Override
    public int deleteUsedEmailApprovedTokenByUserId(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(DELETE_USED_TOKEN,namedParameters);
    }

    @Override
    public UserAccount getUserAccountByVerifiedTokenAndEmail(String email, String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("token", token);
        UserAccount userAccount = jdbcTemplate.queryForObject(VERIFIED_TOKEN,namedParameters,
                new UserAccountSimpleRowMapper());
        return userAccount;
    }
}
