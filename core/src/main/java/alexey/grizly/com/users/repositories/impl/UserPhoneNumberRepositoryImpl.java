package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.repositories.UserPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserPhoneNumberRepositoryImpl implements UserPhoneNumberRepository {
    private static final String COUNT_USAGE_PHONE ="SELECT COUNT(u.phone) FROM users as u where u.phone = :phone;";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserPhoneNumberRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long countOfUsagePhone(String phone) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("phone",phone);
        return jdbcTemplate
                .queryForObject(COUNT_USAGE_PHONE,
                        namedParameters,Long.class);
    }
}
