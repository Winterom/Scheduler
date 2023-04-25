package alexey.grizly.com.users.models;

import alexey.grizly.com.commons.security.EAuthorities;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppAuthorities implements GrantedAuthority {
    private Long id;
    private String title;
    private String description;
    private EAuthorities authorities;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public String getAuthority() {
       return this.authorities.name();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("description", description)
                .append("authorities", authorities)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }


}
