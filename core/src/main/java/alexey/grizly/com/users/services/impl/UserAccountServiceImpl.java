package alexey.grizly.com.users.services.impl;



import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import alexey.grizly.com.users.services.UserAccountService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;



@Service
public class UserAccountServiceImpl implements UserAccountService {

       private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(final UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Nullable
    @Transactional
    public int saveRestorePasswordToken(final Long userId,
                                           final LocalDateTime expireDate,
                                           final String token){
        return userAccountRepository.saveRestorePasswordToken(userId,expireDate,token);
    }

    @Override
    public UserAccount getSimpleUserAccount(final String email){
        return userAccountRepository.getSimpleUserAccount(email);
    }

    @Override
    public boolean updatePassword(final String email, final String passwordHash, final String token){
        return true;
    }

    @Override
    @Transactional
    public Long createNewUserAccount(String email,
                                        String phone,
                                        String passwordHash,
                                        LocalDateTime credentialExpired,
                                        EUserStatus status,
                                        LocalDateTime createdAt) {
        Long userId = userAccountRepository.registrationNewUser(email,
                phone,passwordHash,credentialExpired,status,createdAt);

        return userId;
    }


}
