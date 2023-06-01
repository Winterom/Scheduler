package alexey.grizly.com.users.dtos.response;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UsersRoles;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserProfileResponseDto {
    private Long id;
    private String email;
    private Boolean isEmailVerified;
    private Set<UsersRoles> usersRoles;
    private EUserStatus status;
    private String phone;
    private Boolean isPhoneVerified;
    private LocalDateTime credentialExpiredTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
