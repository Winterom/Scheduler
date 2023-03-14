package alexey.grizly.com.users.models;

import alexey.grizly.com.commons.security.EUserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Table(name = "users")
public class UserAccount implements UserDetails {
    @Id
    private Long id;
    @Column(value = "email")
    private String email;

    @MappedCollection(idColumn = "user_id",keyColumn = "user_id")
    private Set<UsersRoles> usersRoles;
    @Column(value = "e_status")
    private EUserStatus status;

    @Column(value = "is_email_verified")
    private Boolean isEmailVerified;
    @Column(value = "password")
    private String password;

    @Column(value = "credential_expired")
    private LocalDateTime credentialExpiredTime;
    @Column(value = "createdAt")
    private LocalDateTime createdAt;
    @Column(value = "updatedAt")
    private LocalDateTime updatedAt;
    @Transient
    private Set<GrantedAuthority> authorities;

    public Collection<? extends GrantedAuthority> getAuthorities() {
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
        return this.status.equals(EUserStatus.ACTIVE);
    }
}
