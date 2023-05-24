package alexey.grizly.com.users.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class UserAccount implements UserDetails {
    private Long id;
    private String email;
    private Boolean isEmailVerified;
    private Set<UsersRoles> usersRoles;
    private EUserStatus status;
    private String phone;
    private Boolean isPhoneVerified;
    private String password;
    private LocalDateTime credentialExpiredTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<AppAuthorities> authorities;

    public Collection<AppAuthorities> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return !(this.status.equals(EUserStatus.DISMISSED)
                ||this.status.equals(EUserStatus.BANNED)
                ||this.status.equals(EUserStatus.DELETED));
    }

    public boolean isCredentialsNonExpired() {
        return LocalDateTime.now().isBefore(this.credentialExpiredTime);
    }

    public boolean isEnabled() {
        return this.status.equals(EUserStatus.ACTIVE) || this.status.equals(EUserStatus.NEW_USER);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("email", email)
                .append("usersRoles", usersRoles)
                .append("status", status)
                .append("isEmailVerified", isEmailVerified)
                .append("password", password)
                .append("credentialExpiredTime", credentialExpiredTime)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("authorities", authorities.toString())
                .toString();
    }
}
