package alexey.grizly.com.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppGlobalProperties {
    private final String secret = "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g";
    private final Integer jwtLifetime = 3_600_000;
    private Integer jwtRefreshLifetime = 36_000_000;
    private Integer emailVerifyTokenLifeTime = 36_000_000;
}
