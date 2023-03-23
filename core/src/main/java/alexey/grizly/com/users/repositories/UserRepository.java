package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface UserRepository extends org.springframework
        .data.repository.Repository<UserAccount,Long> {

    @Modifying
    @Query("INSERT INTO refresh_tokens (id,expired,token) " +
            "values (:id,:expired,:token) ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token")
    int saveRefreshToken(@Param("id") Long id, @Param("expired")Date expired,@Param("token") String token);
}
