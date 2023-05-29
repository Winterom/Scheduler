package alexey.grizly.com.users.services;

import alexey.grizly.com.users.models.UserAccount;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

public interface RefreshTokenService {
    void generateRefreshToken(final UserAccount userAccount, final Date refreshExpire, final HttpServletResponse response);

    UserAccount checkRefreshToken(String refreshToken);
}
