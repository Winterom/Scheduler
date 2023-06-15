package alexey.grizly.com.users.models.user;


import alexey.grizly.com.users.models.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserProfileWithRolesAndTokens {
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
    private LocalDateTime createAtEmailToken;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Role {
        private String title;
        private String description;
    }
}
