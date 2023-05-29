package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountSimpleRowMapper;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserEmailRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@SuppressWarnings("FieldCanBeLocal")
public class UserEmailRepositoryImpl implements UserEmailRepository {
    private static final String DELETE_USED_TOKEN = "DELETE FROM email_approved_token as eat WHERE eat.userid=:id";
    private static final  String INSERT_TOKEN="INSERT INTO email_approved_token (userid, token, expired) VALUES (:id,:token,:expired) " +
            "ON CONFLICT (userId) DO UPDATE SET token=:token, expired = :expired";

    private static final String VERIFIED_TOKEN = "SELECT * FROM users as u left join email_approved_token eat on u.id = eat.userid" +
            " where eat.token=:token and u.email=:email and eat.expired>CURRENT_TIMESTAMP and u.e_status not in ('BANNED','DISMISSED','DELETED')";
    private static final String UPDATE_USER_EMAIL_STATUS = "UPDATE users set is_email_verified=:status where id=:id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserEmailRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVerifiedEmailToken(final Long userId, final String token, LocalDateTime expired) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("token", token)
                .addValue("expired", expired);
        return jdbcTemplate.update(INSERT_TOKEN,namedParameters);
    }

    @Override
    public int deleteUsedEmailApprovedTokenByUserId(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(DELETE_USED_TOKEN,namedParameters);
    }

    @Override
    @Nullable
    public UserAccount getUserAccountByVerifiedTokenAndEmail(String email, String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("token", token);
        return jdbcTemplate.queryForObject(VERIFIED_TOKEN,namedParameters,
                new UserAccountSimpleRowMapper());

    }
    @Override
    public int updateUserEmailStatusByUserId(Long userId,Boolean status){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("status",status);
        return jdbcTemplate.update(UPDATE_USER_EMAIL_STATUS,namedParameters);
    }
}
