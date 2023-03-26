package alexey.grizly.com.users.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table
@Data
public class ChangePasswordToken {
    @Id
    private Long id;            /*userId*/
    @Column(value = "token")
    private String token;
    @Column(value = "expire")
    private LocalDateTime expire;
}
