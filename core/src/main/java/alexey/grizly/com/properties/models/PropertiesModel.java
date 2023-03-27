package alexey.grizly.com.properties.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "properties")
@Getter
@Setter
public class PropertiesModel {
    @Id
    @Column(value = "clazz")
    private Class<?> clazz;
    @Column(value = "property")
    private String property;/*json объект Class:property*/
}
