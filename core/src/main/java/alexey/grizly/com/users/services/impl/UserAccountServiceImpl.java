package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.ChangePasswordTokenRepository;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import alexey.grizly.com.users.repositories.UserEmailRepository;
import alexey.grizly.com.users.services.UserAccountService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final UserEmailRepository userEmailRepository;
    private final UserAccountRepository userAccountRepository;



    @Autowired
    public UserAccountServiceImpl(final ChangePasswordTokenRepository changePasswordTokenRepository,
                                  final UserEmailRepository userEmailRepository,
                                  final UserAccountRepository userAccountRepository
                                  ) {
        this.changePasswordTokenRepository = changePasswordTokenRepository;
        this.userEmailRepository = userEmailRepository;
        this.userAccountRepository = userAccountRepository;

    }

    @Override
    @Transactional
    public void saveChangePasswordToken(final Long userId,
                                        final LocalDateTime expireDate,
                                        final String token){
        changePasswordTokenRepository.saveChangePasswordToken(userId, expireDate, token);
    }

    @Nullable
    @Override
    public UserAccount getSimpleUserAccount(final String email){
        return userAccountRepository.getSimpleUserAccount(email);
    }


    @Override
    public UserAccount checkPasswordChangeToken(final String email, final String token) {
        return changePasswordTokenRepository.checkPasswordChangeToken(email,token);
    }

    public void deleteUsedChangePasswordTokenByUserId(Long userId){
        changePasswordTokenRepository.deleteUsedToken(userId);
    }

    @Override
    public void updatePassword(final UserAccount userAccount,
                               final String passwordHash,
                               LocalDateTime credentialExpired) {
        userAccountRepository.updatePassword(userAccount.getId(), passwordHash,credentialExpired);
    }


    @Override
    public boolean emailBusyCheck(final String email) {
       return userAccountRepository.countOfUsageEmail(email) > 0;
    }

    @Override
    public boolean phoneBusyCheck(final String phone) {
        return userAccountRepository.countOfUsagePhone(phone) > 0;
    }


}
