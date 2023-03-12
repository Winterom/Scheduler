package alexey.grizly.com.users.query_builders;

import alexey.grizly.com.commons.utils.sql_query.SQLQueryBuilder;
import alexey.grizly.com.commons.utils.url_query.UrlQueryParam;
import alexey.grizly.com.users.models.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class SQLQueryBuilderUserAccountImpl implements SQLQueryBuilder<UserAccount> {
    private final StringBuilder sqlQueryString = new StringBuilder("FROM users AS u ");
    private final Map<String,Object> params =new HashMap<>();
    private final UrlQueryParam urlQueryParam;

    public SQLQueryBuilderUserAccountImpl(UrlQueryParam urlQueryParam) {
        this.urlQueryParam = urlQueryParam;
    }


    @Override
    public SQLQueryBuilder<UserAccount> build() {
        if(!urlQueryParam.getSearchString().isEmpty()){
            this.sqlQueryString.append(" WHERE u.email LIKE :search");
            params.put("search",urlQueryParam.getSearchString());
        }
        return this;
    }

    @Override
    public String getCountQuery() {
        return "SELECT COUNT(*) "+this.sqlQueryString;
    }

    @Override
    public Map<String,Object> getCountQueryParam() {
        return this.params;
    }

    @Override
    public String getMainQuery() {
        return "SELECT * "+this.sqlQueryString;
    }

    @Override
    public Map<String,Object> getMainQueryParam() {
        return this.params;
    }
}
