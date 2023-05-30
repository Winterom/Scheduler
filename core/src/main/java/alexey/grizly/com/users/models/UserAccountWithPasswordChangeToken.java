package alexey.grizly.com.users.models;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class UserAccountWithPasswordChangeToken {
    private Long id;
    private String password;
    private EUserStatus status;
    @Nullable
    private PasswordChangeToken passwordChangeToken;
}
