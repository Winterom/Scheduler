package alexey.grizly.com.commons.utils.sql_query;

import java.util.Map;

public interface SQLQueryBuilder<T> {
    SQLQueryBuilder<T> build();
    String getCountQuery();
    Map<String,Object> getCountQueryParam();
    String getMainQuery();
    Map<String,Object> getMainQueryParam();
}
