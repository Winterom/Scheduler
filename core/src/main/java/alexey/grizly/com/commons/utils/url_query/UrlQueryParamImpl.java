package alexey.grizly.com.commons.utils.url_query;

import alexey.grizly.com.commons.entities.ESortDirection;
import alexey.grizly.com.commons.entities.ESortedFields;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;


public class UrlQueryParamImpl implements UrlQueryParam {
    private Integer page = 1;
    private Integer pageSize = 5;
    private String searchString="";
    private final HashMap<ESortedFields, ESortDirection> sortedParam = new HashMap<>(3);

    @Override
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page==null||page < 1){
            this.page = 1;
        }else {
            this.page = page;
        }

    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize==null||pageSize <1){
            this.pageSize = 5;
        }else {
            this.pageSize = pageSize;
        }
    }

    @Override
    public HashMap<ESortedFields, ESortDirection> getSortedParam() {
        return sortedParam;
    }

    @Override
    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = "%"+searchString.toLowerCase()+"%";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("size", pageSize)
                .append("search", searchString)
                .append("sorted", sortedParam)
                .toString();
    }
}
