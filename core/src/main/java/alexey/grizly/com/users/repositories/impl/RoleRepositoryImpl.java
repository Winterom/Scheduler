package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.roles.AllRolesRowMapper;
import alexey.grizly.com.users.extractors.roles.AuthorityNodeRowMapper;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;
import alexey.grizly.com.users.messages.roles.response.RoleNode;
import alexey.grizly.com.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final String SET_ROLE = "INSERT INTO users_roles (role_id,user_id) VALUES (:roleId,:userId)";
    private static final String ALL_ROLES = "SELECT r.id, r.is_catalog, r.catalog, r.title, r.description," +
            " r.createdat, r.updatedat,r.status, u.email FROM roles as r left join users as u on r.modifyby=u.id";
    private static final String AUTHORITY_BY_ROLE = "SELECT * FROM roles_authorities as r_a LEFT JOIN authorities a " +
            "ON a.id = r_a.authority_id WHERE role_id=:roleId";
    private static final String ALL_AUTHORITIES = "SELECT * FROM authorities";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int setRole(Long userId, Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("roleId",roleId);
        return jdbcTemplate.update(SET_ROLE,namedParameters);
    }

    @Override
    public List<RoleNode.Role> getAllRoles() {
        return jdbcTemplate.getJdbcTemplate().query(ALL_ROLES,new AllRolesRowMapper());
    }

    @Override
    public List<AuthoritiesNode.Authority> getAuthoritiesByRoleId(Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roleId",roleId);
        return jdbcTemplate.query(AUTHORITY_BY_ROLE,namedParameters,new AuthorityNodeRowMapper());
    }

    @Override
    public List<AuthoritiesNode.Authority> getAllAuthorities() {
        return jdbcTemplate.getJdbcTemplate().query(ALL_AUTHORITIES,new AuthorityNodeRowMapper());
    }
}
