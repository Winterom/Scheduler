package alexey.grizly.com.users.models;

import lombok.Data;


import java.time.LocalDateTime;


@Data
public class RestorePasswordToken {

    private Long id;            /*userId*/
    private String token;
    private LocalDateTime expire;
}
