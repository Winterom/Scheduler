package alexey.grizly.com.users.repositories;


import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenRepository {
    int saveRefreshToken(Long id, Date expired, String token);
}
