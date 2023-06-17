package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountWithEmailApprovedTokenExtractor;
import alexey.grizly.com.users.models.user.UserAccountWithEmailApprovedToken;
import alexey.grizly.com.users.repositories.UserEmailRepository;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserEmailRepositoryImpl implements UserEmailRepository {
    private static final String DELETE_USED_TOKEN = "DELETE FROM email_approved_token as eat WHERE eat.userid=:id";
    private static final  String INSERT_TOKEN="INSERT INTO email_approved_token (userid, token, expired, createdat) VALUES (:id,:token,:expired,:create) " +
            "ON CONFLICT (userId) DO UPDATE SET token=:token, expired = :expired, createdat =:create";

    private static final String VERIFIED_TOKEN_BY_EMAIL = "SELECT u.id, u.e_status,u.email, u.is_email_verified, " +
            "eat.token, eat.expired as token_expired, eat.createdat as token_created" +
            " FROM users as u left join email_approved_token eat on u.id = eat.userid" +
            " where u.email=:email";
    private static final String VERIFIED_TOKEN_BY_ID = "SELECT u.id, u.e_status,u.email, u.is_email_verified, " +
            "eat.token, eat.expired as token_expired, eat.createdat as token_created" +
            " FROM users as u left join email_approved_token eat on u.id = eat.userid" +
            " where u.id=:id";
    private static final String UPDATE_USER_EMAIL_STATUS = "UPDATE users set is_email_verified=:status where id=:id";
    private static final String COUNT_USAGE_EMAIL="SELECT COUNT(u.email) FROM users as u where u.email = :email;";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserEmailRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVerifiedEmailToken(final Long userId, final String token, LocalDateTime expired) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("create",LocalDateTime.now())
                .addValue("id", userId)
                .addValue("token", token)
                .addValue("expired", expired);
        return jdbcTemplate.update(INSERT_TOKEN,namedParameters);
    }

    @Override
    public int deleteUsedEmailApprovedTokenByUserId(final Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(DELETE_USED_TOKEN,namedParameters);
    }

    @Override
    @Nullable
    public UserAccountWithEmailApprovedToken getUserAccountWithVerifiedToken(final String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email);
        return jdbcTemplate.query(VERIFIED_TOKEN_BY_EMAIL,namedParameters,
                new UserAccountWithEmailApprovedTokenExtractor());

    }

    @Override
    public UserAccountWithEmailApprovedToken getUserAccountWithVerifiedToken(Long userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId);
        return jdbcTemplate.query(VERIFIED_TOKEN_BY_ID,namedParameters,
                new UserAccountWithEmailApprovedTokenExtractor());
    }

    @Override
    public int updateUserEmailStatusByUserId(final Long userId, final Boolean status){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("status",status);
        return jdbcTemplate.update(UPDATE_USER_EMAIL_STATUS,namedParameters);
    }

    @Override
    public Long countOfUsageEmail(@NonNls final String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate
                .queryForObject(COUNT_USAGE_EMAIL,
                        namedParameters,Long.class);
    }
}
