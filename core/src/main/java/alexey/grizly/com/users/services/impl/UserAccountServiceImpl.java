package alexey.grizly.com.users.services.impl;



import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.ChangePasswordTokenRepository;
import alexey.grizly.com.users.repositories.EmailApprovedRepository;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import alexey.grizly.com.users.services.RoleForUserService;
import alexey.grizly.com.users.services.UserAccountService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final EmailApprovedRepository emailApprovedRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleForUserService roleForUserService;



    @Autowired
    public UserAccountServiceImpl(final ChangePasswordTokenRepository changePasswordTokenRepository,
                                  final EmailApprovedRepository emailApprovedRepository,
                                  final UserAccountRepository userAccountRepository,
                                  final RoleForUserService roleForUserService
                                  ) {
        this.changePasswordTokenRepository = changePasswordTokenRepository;
        this.emailApprovedRepository = emailApprovedRepository;
        this.userAccountRepository = userAccountRepository;
        this.roleForUserService = roleForUserService;

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
    @Override
    public void updatePassword(final UserAccount userAccount,
                               final String passwordHash,
                               LocalDateTime credentialExpired) {
        userAccountRepository.updatePassword(userAccount.getId(), passwordHash,credentialExpired);
    }
    @Override
    public int saveApprovedEmailToken(final Long userId, final String token) {
        return emailApprovedRepository.saveVerifiedEmailToken(userId,token);
    }

    @Override
    @Transactional
    public UserAccount createNewUserAccount(final String email,
                                            final String phone,
                                            final String passwordHash,
                                            final LocalDateTime credentialExpired,
                                            final EUserStatus status,
                                            final LocalDateTime createdAt) {
        Long userId = userAccountRepository.registrationNewUser(email,
                phone,passwordHash,credentialExpired,status,createdAt);
        roleForUserService.setDefaultRoleForNewUser(userId);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        userAccount.setEmail(email);
        userAccount.setPhone(phone);
        userAccount.setStatus(status);
        userAccount.setCredentialExpiredTime(credentialExpired);
        userAccount.setCreatedAt(createdAt);
        return userAccount;
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
