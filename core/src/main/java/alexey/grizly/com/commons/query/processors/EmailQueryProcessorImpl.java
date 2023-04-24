package alexey.grizly.com.commons.query.processors;

import alexey.grizly.com.commons.query.HttpQueryParams;
import alexey.grizly.com.commons.query.SqlQueryProcessor;
import alexey.grizly.com.commons.query.type.ESortDirection;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.MultiValueMap;

public class EmailQueryProcessorImpl implements SqlQueryProcessor {
    private final StringBuilder query = new StringBuilder("SELECT * FROM email_properties ");
    @Override
    @Nullable
    public String createSqlQuery(HttpQueryParams params) {
        if(params==null){
            return query.toString();
        }
        if(params.sortedFields()!=null){
            appendSorted(params.sortedFields());
        }
    }

    private void appendSorted(MultiValueMap<ESortDirection,String> sortedFields){

    }
}
