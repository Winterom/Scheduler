package alexey.grizly.com.users.repositories;


import alexey.grizly.com.users.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenRepository {
    void saveRefreshToken(Long id, Date expired, String token);

    UserAccount checkRefreshToken(String refreshToken);
}
