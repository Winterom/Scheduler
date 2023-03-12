package alexey.grizly.com.commons.entities;

import java.util.HashMap;

public enum ESortDirection {
    ASC("asc"," ASC"),
    DESC("desc"," DESC");
    private final String direction;
    private final String  alias;

    public String getDirection() {
        return this.direction;
    }
    public String getAlias(){
        return this.alias;
    }

    ESortDirection(String alias, String direction){
        this.alias = alias;
        this.direction = direction;
    }
    private static final HashMap<String,ESortDirection> LOOKUP_MAP = new HashMap<>();
    static {
        for (ESortDirection direction: values()) {
            LOOKUP_MAP.put(direction.alias, direction);
        }
    }


    public static ESortDirection getByDirection(String direction){
        return LOOKUP_MAP.get(direction);
    }
}
