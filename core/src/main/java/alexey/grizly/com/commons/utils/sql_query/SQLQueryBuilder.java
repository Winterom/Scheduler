package alexey.grizly.com.commons.utils.sql_query;

import alexey.grizly.com.commons.utils.url_query.UrlQueryParam;

import java.util.Map;

public interface SQLQueryBuilder<T> {
    SQLQueryBuilder<T> build();
    String getCountQuery();
    Map<String,Object> getCountQueryParam();
    String getMainQuery();
    Map<String,Object> getMainQueryParam();
}
