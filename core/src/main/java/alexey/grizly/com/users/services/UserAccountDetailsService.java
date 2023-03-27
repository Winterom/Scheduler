package alexey.grizly.com.users.services;


import alexey.grizly.com.users.extractors.UserAccountWithAuthoritiesExtractor;
import alexey.grizly.com.users.validators.PhoneNumberValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
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
    public UserDetails loadUserByUsername(String authToken) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        EmailValidator emailValidator = new EmailValidator();
        if(emailValidator.isValid(authToken,null)){
            userDetails = loadByEmail(authToken);
        }
        PhoneNumberValidator phoneValidator = new PhoneNumberValidator();
        if(phoneValidator.isValid(authToken,null)){
            userDetails = loadByPhone(authToken);
        }
        if(userDetails==null){
            throw new UsernameNotFoundException("Неверные учетные данные пользователя");
        }
        return userDetails;
    }

    private UserDetails loadByEmail(String email){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.
                query("SELECT u.id,u.email,u.e_status,u.password,u.credential_expired, a.e_authorities FROM users as u " +
                                "left join users_roles ur on u.id = ur.user_id " +
                                "left join roles r on ur.role_id = r.id " +
                                "left join roles_authorities ra on r.id = ra.role_id " +
                                "left join authorities a on ra.authority_id = a.id " +
                                "where u.email=:email and u.e_status='ACTIVE'"
                        ,namedParameters,new UserAccountWithAuthoritiesExtractor());
    }
    private UserDetails loadByPhone(String phone){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("phone", phone);
        return jdbcTemplate.
                query("SELECT u.id,u.email,u.e_status,u.password,u.credential_expired, a.e_authorities FROM users as u " +
                                "left join users_roles ur on u.id = ur.user_id " +
                                "left join roles r on ur.role_id = r.id " +
                                "left join roles_authorities ra on r.id = ra.role_id " +
                                "left join authorities a on ra.authority_id = a.id " +
                                "where u.phone=:phone and u.e_status='ACTIVE'"
                        ,namedParameters,new UserAccountWithAuthoritiesExtractor());
    }
}
