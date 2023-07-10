package alexey.grizly.com.users.repositories.impl;

import alexey.grizly.com.users.extractors.roles.AuthorityNodeRowMapper;
import alexey.grizly.com.users.extractors.roles.CheckedRoleExtractor;
import alexey.grizly.com.users.extractors.roles.RolesTreeExtractor;
import alexey.grizly.com.users.messages.roles.response.AuthoritiesNodeResponseMessage;
import alexey.grizly.com.users.messages.roles.response.RolesTree;
import alexey.grizly.com.users.models.roles.CheckedRoleForDelete;
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
    private static final String ROLES_TREE = "WITH RECURSIVE temp_roles (id, is_catalog, parent_id, title, description, createdat, updatedat, status, modifyby, path) AS " +
            "( " +
            "SELECT t1.id, t1.is_catalog, t1.parent_id, t1.title, t1.description, t1.createdat, t1.updatedat, t1.status, t1.modifyby, cast('' as VARCHAR(100)) as path " +
            "FROM roles as t1 " +
            "WHERE t1.id =:roleId " +
            " UNION " +
            "SELECT t2.id, t2.is_catalog, t2.parent_id, t2.title, t2.description, t2.createdat, t2.updatedat, t2.status, t2.modifyby, " +
            "cast(temp_roles.path||'\\' || t2.title as VARCHAR(100)) FROM roles as t2 " +
            "join temp_roles on (t2.parent_id = temp_roles.id) " +
            ") " +
            "SELECT r.id, r.is_catalog, r.parent_id, r.title, r.description, r.createdat, r.updatedat, r.status,  r.path, u.email as email " +
            "FROM temp_roles as r left join users as u on r.modifyby=u.id";
    private static final String ROLES_WITH_USERS_IN_TREE = "WITH RECURSIVE temp_roles (id, is_catalog, parent_id, title, status, path) AS " +
            "( " +
            "SELECT t1.id, t1.is_catalog, t1.parent_id, t1.title, t1.status, cast('' as VARCHAR(100)) as path " +
            "FROM roles as t1 " +
            "WHERE t1.id =:roleId " +
            " UNION " +
            "SELECT t2.id, t2.is_catalog, t2.parent_id, t2.title, t2.status, " +
            "cast(temp_roles.path||'\\' || t2.title as VARCHAR(100)) FROM roles as t2 " +
            "join temp_roles on (t2.parent_id = temp_roles.id) " +
            ") " +
            "SELECT r.id, r.is_catalog, r.parent_id, r.title, r.status,  r.path, u.email as email, u.id as user_id " +
            "FROM temp_roles as r left join users_roles as ur on r.id = ur.role_id left join users u on u.id = ur.user_id";
    private static final String AUTHORITY_BY_ROLE = "SELECT * FROM roles_authorities as r_a LEFT JOIN authorities a " +
            "ON a.id = r_a.authority_id WHERE role_id=:roleId";
    private static final String ALL_AUTHORITIES = "SELECT * FROM authorities";

    private static final String ROLE_SAVE_AFTER_DRAG_DROP = "UPDATE roles set parent_id=:newParentId where id=:roleId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int setRole(final Long userId, final Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("roleId",roleId);
        return jdbcTemplate.update(SET_ROLE,namedParameters);
    }

    @Override
    public RolesTree.RoleNode getRolesTree(final Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roleId",roleId);
        return jdbcTemplate.query(ROLES_TREE,namedParameters,new RolesTreeExtractor());
    }

    @Override
    public CheckedRoleForDelete getRolesWithAssignedUsers(final Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roleId",roleId);
        return jdbcTemplate.query(ROLES_WITH_USERS_IN_TREE,namedParameters,new CheckedRoleExtractor());
    }

    @Override
    public List<AuthoritiesNodeResponseMessage.Authority> getAuthoritiesByRoleId(final Long roleId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roleId",roleId);
        return jdbcTemplate.query(AUTHORITY_BY_ROLE,namedParameters,new AuthorityNodeRowMapper());
    }

    @Override
    public List<AuthoritiesNodeResponseMessage.Authority> getAllAuthorities() {
        return jdbcTemplate.getJdbcTemplate().query(ALL_AUTHORITIES,new AuthorityNodeRowMapper());
    }

    @Override
    public int updateRoleAfterDragDrop(final Long roleId, final Long newParentId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("newParentId", newParentId)
                .addValue("roleId",roleId);
        return jdbcTemplate.update(ROLE_SAVE_AFTER_DRAG_DROP,namedParameters);
    }

}
