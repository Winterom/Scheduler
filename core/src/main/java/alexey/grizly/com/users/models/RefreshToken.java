package alexey.grizly.com.users.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "refresh_tokens")
@Data
public class RefreshToken {
    @Id
    private Long id;
    @Column(value = "expired")
    private LocalDateTime expired;
    @Column(value = "token")
    private String token;
}
