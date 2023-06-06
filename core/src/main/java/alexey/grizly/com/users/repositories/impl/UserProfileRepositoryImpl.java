package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.extractors.UserAccountWithRolesExtractor;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {
    private static final String GET_PROFILE = "SELECT u.id, u.email, u.is_email_verified, u.e_status, u.phone," +
            " u.is_phone_verified, u.credential_expired, u.createdat, u.updatedat, r.title, r.description" +
            " FROM users as u LEFT JOIN users_roles ur on u.id = ur.user_id " +
            " LEFT JOIN roles r on ur.role_id = r.id where u.email =:email";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserProfileRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserProfileResponse getUserAccountWithRoles(String email){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email);
        return jdbcTemplate.query(GET_PROFILE,namedParameters,new UserAccountWithRolesExtractor());
    }
}
