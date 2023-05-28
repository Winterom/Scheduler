package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.UserAccountSimpleRowMapper;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.ChangePasswordTokenRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class ChangePasswordTokenRepositoryImpl implements ChangePasswordTokenRepository {
    private final String SAVE_RESTORE_PASSWORD_TOKEN="INSERT INTO restore_psw_token(id, token, expire) VALUES (:id,:token,:expire) "+
            "ON CONFLICT (id) DO UPDATE SET token = :token, expire = :expire;";
    /*TODO проверка истекшего токена!!!*/
    private final String CHECK_TOKEN = "SELECT * FROM users as u left join restore_psw_token rpt on u.id = rpt.id" +
            " where u.email=:email and rpt.token and (CURRENT_TIMESTAMP < rpt.expire)";
    private final String DELETE_USED_TOKEN = "DELETE FROM restore_psw_token as rpt WHERE rpt.id=:id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ChangePasswordTokenRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int saveChangePasswordToken(final Long userId, final LocalDateTime expireDate, final String token){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("expire", expireDate)
                .addValue("token", token);
        return jdbcTemplate.update(SAVE_RESTORE_PASSWORD_TOKEN, namedParameters);
    }

    @Override
    @Nullable
    public UserAccount checkPasswordChangeToken(String email, String token) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email",email)
                .addValue("token",token);
        try {
        return jdbcTemplate.queryForObject(CHECK_TOKEN,namedParameters,new UserAccountSimpleRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void deleteUsedToken(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(DELETE_USED_TOKEN,namedParameters);
    }
}
