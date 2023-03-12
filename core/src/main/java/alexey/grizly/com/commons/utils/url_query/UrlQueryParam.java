package alexey.grizly.com.commons.utils.url_query;

import alexey.grizly.com.commons.entities.ESortDirection;
import alexey.grizly.com.commons.entities.ESortedFields;

import java.util.HashMap;

public interface UrlQueryParam {
    Integer getPage();
    Integer getPageSize();
    HashMap<ESortedFields, ESortDirection> getSortedParam();
    String getSearchString();
}
