package alexey.grizly.com.commons.query;

import alexey.grizly.com.commons.query.type.ESortDirection;
import org.springframework.util.MultiValueMap;


public interface HttpQueryParams {
    MultiValueMap<ESortDirection, String> sortedFields();
    MultiValueMap<String,String> filterFields();
}
