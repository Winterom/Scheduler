package alexey.grizly.com.users.models;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EmailApprovedToken{
    private Long userId;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expired;
}
