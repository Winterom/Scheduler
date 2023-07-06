package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.roles.AllRolesExtractor;
import alexey.grizly.com.users.extractors.roles.AuthorityNodeRowMapper;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNode;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
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
    private static final String ALL_ROLES = "WITH RECURSIVE temp_roles (id, is_catalog, parent_id, title, description, createdat, updatedat, status, modifyby, path) AS " +
            "( " +
            "SELECT t1.id, t1.is_catalog, t1.parent_id, t1.title, t1.description, t1.createdat, t1.updatedat, t1.status, t1.modifyby, cast('' as VARCHAR(100)) as path " +
            "FROM roles as t1 " +
            "WHERE t1.id = 1 " +
            " UNION " +
            "SELECT t2.id, t2.is_catalog, t2.parent_id, t2.title, t2.description, t2.createdat, t2.updatedat, t2.status, t2.modifyby, " +
            "cast(temp_roles.path||'\\' || t2.title as VARCHAR(100)) FROM roles as t2 " +
            "join temp_roles on (t2.parent_id = temp_roles.id) " +
            ") " +
            "SELECT r.id, r.is_catalog, r.parent_id, r.title, r.description, r.createdat, r.updatedat, r.status,  r.path, u.email as email " +
            "FROM temp_roles as r left join users as u on r.modifyby=u.id";
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
    public RolesTree.RoleNode getAllRolesTree() {
        return jdbcTemplate.getJdbcTemplate().query(ALL_ROLES,new AllRolesExtractor());
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
