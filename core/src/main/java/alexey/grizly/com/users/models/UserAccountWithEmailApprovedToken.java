package alexey.grizly.com.users.models;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Data
public class UserAccountWithEmailApprovedToken{
    private Long id;
    private EUserStatus status;
    private String email;
    private Boolean isEmailVerified;
    @Nullable
    private EmailApprovedToken token;
}
