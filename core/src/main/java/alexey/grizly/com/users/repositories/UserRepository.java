package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends org.springframework
        .data.repository.Repository<UserAccount,Long> {
    @Query("SELECT * FROM users as u WHERE u.id=:id")
    Optional<UserAccount> getById (@Param("id")Long id);

    @Query("SELECT u,r FROM users as u left join users_roles ur on u.id = ur.user_id left join roles r on ur.role_id " +
            "= r.id")
    Optional<UserAccount> geByIdWithRoles(@Param("id")Long id);

    @Query("SELECT count(*) FROM users")
    Integer countUserAccount();

    @Query("SELECT * FROM users as u where u.email=:email")
    Optional<UserAccount> findByEmail(@Param("email") String email);

    @Modifying
    @Query("INSERT INTO refresh_tokens (id,expired,token) " +
            "values (:id,:expired,:token) ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token")
    int saveRefreshToken(@Param("id") Long id, @Param("expired")Date expired,@Param("token") String token);
}
