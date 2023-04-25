package alexey.grizly.com.users.models;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class RefreshToken {
    private Long id;
    private LocalDateTime expired;
    private String token;
}
