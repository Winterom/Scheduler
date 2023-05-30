package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountWithAuthoritiesExtractor;
import alexey.grizly.com.users.repositories.UserDetailsRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsRepositoryImpl implements UserDetailsRepository {
    private static final String LOAD_BY_EMAIL ="SELECT u.id,u.email,u.e_status,u.password,u.credential_expired, a.e_authorities FROM users as u " +
            "left join users_roles ur on u.id = ur.user_id " +
            "left join roles r on ur.role_id = r.id " +
            "left join roles_authorities ra on r.id = ra.role_id " +
            "left join authorities a on ra.authority_id = a.id " +
            "where u.email=:email";
    private static final String LOAD_BY_PHONE ="SELECT u.id,u.email,u.e_status,u.password,u.credential_expired, a.e_authorities FROM users as u " +
            "left join users_roles ur on u.id = ur.user_id " +
            "left join roles r on ur.role_id = r.id " +
            "left join roles_authorities ra on r.id = ra.role_id " +
            "left join authorities a on ra.authority_id = a.id " +
            "where u.phone=:phone";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDetailsRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadByEmail(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.
                query(LOAD_BY_EMAIL,namedParameters,new UserAccountWithAuthoritiesExtractor());
    }

    @Override
    public UserDetails loadByPhone(String phone) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("phone", phone);
        return jdbcTemplate.
                query(LOAD_BY_PHONE,namedParameters,new UserAccountWithAuthoritiesExtractor());
    }
}
