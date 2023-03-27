package alexey.grizly.com.users.repositories;

import alexey.grizly.com.users.models.UserAccount;

import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface UserRepository extends org.springframework
        .data.repository.Repository<UserAccount,Long> {


}
