package alexey.grizly.com.commons.query.resolvers;

import alexey.grizly.com.commons.query.HttpQueryParams;
import alexey.grizly.com.commons.query.HttpQueryResolver;
import alexey.grizly.com.commons.query.params.HttpQueryParamsImpl;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class HttpQueryResolverImpl implements HttpQueryResolver {
    @Nullable
    @Override
    public HttpQueryParams resolveAndValid(MultiValueMap<String, String> rawHttpQueryParam) {
        if(rawHttpQueryParam==null||rawHttpQueryParam.isEmpty()){
            return null;
        }

        return new HttpQueryParamsImpl();

    }

    private void resolveSortedParams(List<String> sortedParams){

    }
}
