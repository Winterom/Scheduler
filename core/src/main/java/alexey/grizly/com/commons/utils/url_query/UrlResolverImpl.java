package alexey.grizly.com.commons.utils.url_query;


import alexey.grizly.com.commons.entities.ESortDirection;
import alexey.grizly.com.commons.entities.ESortedFields;



import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UrlResolverImpl implements UrlResolver {
    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.
            LogManager.getLogger(UrlResolverImpl.class);
    @Override
    public UrlQueryParam resolveQueryString(String httpQuery) {
        if (httpQuery!=null){
            httpQuery = URLDecoder.decode(httpQuery, StandardCharsets.UTF_8);
        }
        if(httpQuery==null||httpQuery.isEmpty()||httpQuery.length()>450){
            return loadDefault();
        }
        UrlQueryParamImpl param = new UrlQueryParamImpl();
        String[] paramArray = httpQuery.split("&");
        for(String row: paramArray){
            String[] row1 = row.split("=");
            if (row1.length != 2) {
                continue;
            }
            switch (row1[0]) {
                case "page" -> param.setPage(Integer.valueOf(row1[1]));
                case "size" -> param.setPageSize(Integer.valueOf(row1[1]));
                case "sort" -> resolveSortedParam(param, row1);
                case "search" -> param.setSearchString(row1[1]);
                default -> log.error("Illegal parameter in "+httpQuery+" as "+row1[0]);
            }
        }
        System.out.println(param);
        return param;
    }

    private UrlQueryParam loadDefault(){
        UrlQueryParamImpl param = new UrlQueryParamImpl();
        param.setPageSize(5);
        param.setPage(1);
        param.getSortedParam().put(ESortedFields.NAME, ESortDirection.ASC);
        return param;
    }

    private void resolveSortedParam(UrlQueryParamImpl param, String[] row1){
        String[] val1 = row1[1].split(",");
        for(String val2: val1){
            String[] sortField = val2.split("_");
            if(sortField.length!=2){
                continue;
            }
            param.getSortedParam().put(ESortedFields.getByAlias(sortField[0])
                    ,ESortDirection.getByDirection(sortField[1]));
        }
    }
}
