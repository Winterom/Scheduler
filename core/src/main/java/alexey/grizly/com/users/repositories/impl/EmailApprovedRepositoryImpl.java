package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.repositories.EmailApprovedRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmailApprovedRepositoryImpl implements EmailApprovedRepository {
    private final String CHECK_TOKEN = "SELECT * FROM users as u left join email_approved_token as eat " +
            "on u.id = eat.userid where u.email=:email and eat.token=:token";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmailApprovedRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVerifiedEmailToken(final Long userId, final String token) {
        return 0;
    }

    @Override
    public int checkVerifiedEmailToken(final Long userId, final String token) {
        return 0;
    }
}
