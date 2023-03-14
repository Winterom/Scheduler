package alexey.grizly.com.users.service;

import alexey.grizly.com.users.extractors.UserAccountWithAuthoritiesExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public UserAccountDetailsService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.
                query("SELECT * FROM users as u " +
                                "left join users_roles ur on u.id = ur.user_id " +
                                "left join roles r on ur.role_id = r.id " +
                                "left join roles_authorities ra on r.id = ra.role_id " +
                                "left join authorities a on ra.authority_id = a.id " +
                                "where u.email=:email and u.e_status='ACTIVE'"
                        ,namedParameters,new UserAccountWithAuthoritiesExtractor());
    }
}
