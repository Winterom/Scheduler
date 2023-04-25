package alexey.grizly.com.properties.models;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class PropertiesModel {
    private Class<?> clazz;
    private String property;/*json объект Class:property*/
}
