package alexey.grizly.com.users.messages.response;

import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.user.UserProfileWithRolesAndTokens;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
    private SendVerifyToken emailVerifyToken;

    public UserProfileResponse(UserProfileWithRolesAndTokens userProfile){
        this.id = userProfile.getId();
        this.email = userProfile.getEmail();
        this.isEmailVerified = userProfile.getIsEmailVerified();
        this.status = userProfile.getStatus();
        this.phone = userProfile.getPhone();
        this.isPhoneVerified = userProfile.getIsPhoneVerified();
        this.credentialExpiredTime = userProfile.getCredentialExpiredTime();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
        this.roles = userProfile.getRoles().stream()
                .map(x->new Role(x.getTitle(),x.getDescription())).collect(Collectors.toSet());
    }
    @Data
    @AllArgsConstructor
    public static class Role {
        private String title;
        private String description;
    }



}
