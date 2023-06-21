package alexey.grizly.com.users.messages.profile.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class SendVerifyToken {
    Long nextTokenAfter;

}
