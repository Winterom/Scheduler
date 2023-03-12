package alexey.grizly.com.commons.utils.url_query;

public enum EQueryParamsName {
    PAGE("page"),
    SIZE("size"),
    SORT("sort"),
    SEARCH("search");

    private final String  alias;

    public String getAlias(){
        return this.alias;
    }

    EQueryParamsName(String alias){
        this.alias = alias;
    }
}
