package alexey.grizly.com.users.service;

import alexey.grizly.com.commons.entities.Paging;
import alexey.grizly.com.commons.utils.sql_query.SQLQueryBuilder;
import alexey.grizly.com.commons.utils.url_query.UrlQueryParam;
import alexey.grizly.com.users.extractors.UserAccountWithRoleExtractor;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.query_builders.SQLQueryBuilderUserAccountImpl;
import alexey.grizly.com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountService(UserRepository userRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Paging<UserAccount> getAllByPage(UrlQueryParam param){
        SQLQueryBuilder<UserAccount> sqlQueryBuilder = new SQLQueryBuilderUserAccountImpl(param).build();
        Integer countResult = jdbcTemplate.queryForObject(sqlQueryBuilder.getCountQuery(), sqlQueryBuilder.getCountQueryParam(), Integer.class);
        List<UserAccount> result =jdbcTemplate.query(sqlQueryBuilder.getMainQuery(), sqlQueryBuilder.getMainQueryParam(),new UserAccountWithRoleExtractor());
        Paging<UserAccount> dto = new Paging<>(countResult, param.getPageSize(), param.getPage());
        dto.setContent(result);
        return dto;
    }

    public Optional<UserAccount> getUserById(Long id){
        if(id==null){
            return Optional.empty();
        }
        return userRepository.getById(id);
    }
    public Optional<UserAccount> getUserByIdWithRoles(Long id){
        if(id==null){
            return Optional.empty();
        }
        return userRepository.geByIdWithRoles(id);
    }
}
