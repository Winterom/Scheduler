package alexey.grizly.com.users.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserAccountGetByIdResponseDto {
    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
