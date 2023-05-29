package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserEmailRepository;
import alexey.grizly.com.users.services.UserEmailService;
import alexey.grizly.com.users.utils.ApprovedTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserEmailServiceImpl implements UserEmailService {
    private final UserEmailRepository userEmailRepository;
    private final SecurityProperties securityProperties;

    @Autowired
    public UserEmailServiceImpl(final UserEmailRepository userEmailRepository,
                                final SecurityProperties securityProperties) {
        this.userEmailRepository = userEmailRepository;
        this.securityProperties = securityProperties;
    }

    @Override
    @Transactional
    public boolean approvedUserEmail(final String email, final String token) {
         UserAccount userAccount = userEmailRepository.getUserAccountByVerifiedTokenAndEmail(email,token);
         if (userAccount==null){
             return false;
         }
         userEmailRepository.updateUserEmailStatusByUserId(userAccount.getId(),true);
         userEmailRepository.deleteUsedEmailApprovedTokenByUserId(userAccount.getId());
         return true;
    }

    @Override
    @Transactional
    public String generateVerifiedEmailToken(final Long userId) {
        String token = ApprovedTokenUtils.generateApprovedToken(securityProperties.getApprovedEmailProperty().getApprovedEmailTokenLength());
        LocalDateTime expired = LocalDateTime.now().plus(securityProperties.getApprovedEmailProperty().getApprovedEmailTokenLifetime(),
                securityProperties.getApprovedEmailProperty().getUnit());
        userEmailRepository.saveVerifiedEmailToken(userId,token,expired);
        return token;
    }
}
