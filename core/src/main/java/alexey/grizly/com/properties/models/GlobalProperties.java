package alexey.grizly.com.properties.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

@Data
@Component
public class GlobalProperties {
    private String host= "http://localhost:4200/";
    private TimeZone timeZone = SimpleTimeZone.getTimeZone("Moscow");
}
