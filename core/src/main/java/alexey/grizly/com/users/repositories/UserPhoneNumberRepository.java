package alexey.grizly.com.users.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneNumberRepository {

    Long countOfUsagePhone(final String phone);
}
