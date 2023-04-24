package alexey.grizly.com.commons.query.params;

import alexey.grizly.com.commons.query.HttpQueryParams;
import alexey.grizly.com.commons.query.type.ESortDirection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpQueryParamsImpl implements HttpQueryParams {
    private MultiValueMap<ESortDirection, String> sortedParams;
    private MultiValueMap<String, String> filteredFields;

    @Override
    public MultiValueMap<ESortDirection, String> sortedFields() {
        return this.sortedParams;
    }

    @Override
    public MultiValueMap<String, String> filterFields() {
        return this.filteredFields;
    }
}
