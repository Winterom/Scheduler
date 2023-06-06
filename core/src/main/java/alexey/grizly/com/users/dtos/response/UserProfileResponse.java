package alexey.grizly.com.users.dtos.response;

import alexey.grizly.com.users.models.EUserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserProfileResponse {
    private Long id;
    private String email;
    private Boolean isEmailVerified;
    private EUserStatus status;
    private String phone;
    private Boolean isPhoneVerified;
    private LocalDateTime credentialExpiredTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Role> roles;
    @Data
    public static class Role {
        private String title;
        private String description;
    }
}
