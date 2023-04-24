package alexey.grizly.com.commons.query;

import org.springframework.util.MultiValueMap;

public interface HttpQueryResolver {
    HttpQueryParams resolveAndValid(MultiValueMap<String,String> rawQueryParam);
}
