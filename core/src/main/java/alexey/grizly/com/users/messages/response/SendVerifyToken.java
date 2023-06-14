package alexey.grizly.com.users.messages.response;

import lombok.Data;
import java.time.temporal.ChronoUnit;

@Data
public class SendVerifyToken {
    Long nextTokenAfter;
    ChronoUnit unit;
}
