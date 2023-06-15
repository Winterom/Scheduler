package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.extractors.UserAccountWithRolesExtractor;
import alexey.grizly.com.users.models.user.UserProfileWithRolesAndTokens;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {
    private static final String GET_PROFILE = "SELECT u.id, u.email, u.is_email_verified, u.e_status, u.phone," +
            " u.is_phone_verified, u.credential_expired, u.createdat, u.updatedat, r.title, r.description, eat.createdat as email_token_create" +
            " FROM users as u LEFT JOIN users_roles ur on u.id = ur.user_id " +
            " LEFT JOIN roles r on ur.role_id = r.id " +
            " LEFT JOIN email_approved_token eat on u.id = eat.userid where u.email =:email";
    private static final String UPDATE_PROFILE ="UPDATE users as u SET email=:email,phone=:phone," +
            " updatedat=:updateAt, is_email_verified=false, is_phone_verified=false WHERE id=:id";
    private static final String UPDATE_EMAIL = "UPDATE users as u SET email=:email," +
            "  updatedat=:updateAt, is_email_verified=false WHERE u.id =:id";
    private static final String UPDATE_PHONE = "UPDATE users as u SET phone=:phone," +
            " updatedat=:updateAt, is_phone_verified=false WHERE u.id=:id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserProfileRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public UserProfileWithRolesAndTokens getUserAccountWithRoles(final String email){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate.query(GET_PROFILE,namedParameters,new UserAccountWithRolesExtractor());
    }

    @Override
    public int updateUserProfile(final Long id,
                                 final String email,
                                 final String phone,
                                 final LocalDateTime updateAt) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email)
                .addValue("id",id)
                .addValue("phone",phone)
                .addValue("updateAt",updateAt);
        return jdbcTemplate.update(UPDATE_PROFILE,namedParameters);
    }

    @Override
    public int updateEmail(final Long id, final String email, final LocalDateTime updateAt) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email)
                .addValue("id",id)
                .addValue("updateAt",updateAt);
        return jdbcTemplate.update(UPDATE_EMAIL,namedParameters);
    }


    @Override
    public int updatePhone(final Long id, final String phone, final LocalDateTime updateAt) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("phone",phone)
                .addValue("updateAt",updateAt);
        return jdbcTemplate.update(UPDATE_PHONE,namedParameters);
    }


}
