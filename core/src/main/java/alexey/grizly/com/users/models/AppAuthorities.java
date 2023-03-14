package alexey.grizly.com.users.models;

import alexey.grizly.com.commons.security.EAuthorities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "authorities")
public class AppAuthorities implements GrantedAuthority {
    @Id
    private Long id;
    @Column(value = "title")
    private String title;
    @Column(value = "description")
    private String description;
    @Column(value = "e_authorities")
    private EAuthorities authorities;
    @Column(value = "createdAt")
    private LocalDateTime createdAt;
    @Column(value = "updatedAt")
    private LocalDateTime updatedAt;

    @Override
    public String getAuthority() {
       return null;
    }
}
