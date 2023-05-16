package alexey.grizly.com.users.services;

import java.util.Date;

public interface RefreshTokenService {
    int saveRefreshToken(final Long userId, final String refreshToken, final Date refreshExpire);
}
