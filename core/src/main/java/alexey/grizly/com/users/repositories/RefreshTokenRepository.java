package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.RefreshToken;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenRepository extends org.springframework
        .data.repository.Repository<RefreshToken,Long>{
    @Modifying
    @Query("INSERT INTO refresh_tokens (id,expired,token) " +
            "values (:id,:expired,:token) ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token")
    int saveRefreshToken(@Param("id") Long id, @Param("expired") Date expired, @Param("token") String token);
}
