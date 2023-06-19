package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.events.UserEmailApprovedTokenEvent;
import alexey.grizly.com.users.events.UserAccountChangeEvent;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.EmailApprovedToken;
import alexey.grizly.com.users.models.user.UserAccountWithEmailApprovedToken;
import alexey.grizly.com.users.repositories.UserEmailRepository;
import alexey.grizly.com.users.services.UserEmailService;
import alexey.grizly.com.users.utils.ApprovedTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEmailServiceImpl implements UserEmailService {
    private final UserEmailRepository userEmailRepository;
    private final SecurityProperties securityProperties;
    private final ApplicationEventMulticaster eventMulticaster;

    @Autowired
    public UserEmailServiceImpl(final UserEmailRepository userEmailRepository,
                                final SecurityProperties securityProperties,
                                final ApplicationEventMulticaster eventMulticaster) {
        this.userEmailRepository = userEmailRepository;
        this.securityProperties = securityProperties;
        this.eventMulticaster = eventMulticaster;
    }

    @Override
    @Transactional
    public List<String> generateVerifiedEmailToken(final Long userId) {
        UserAccountWithEmailApprovedToken userAccount = userEmailRepository.getUserAccountWithVerifiedToken(userId);
        return generateVerifiedToken(userAccount);
    }

    @Override
    public List<String> generateVerifiedEmailToken(String email) {
        UserAccountWithEmailApprovedToken userAccount = userEmailRepository.getUserAccountWithVerifiedToken(email);
        return generateVerifiedToken(userAccount);
    }

    private List<String> generateVerifiedToken(UserAccountWithEmailApprovedToken userAccount){
        List<String> errorMessage = new ArrayList<>();
        if (userAccount==null){
            errorMessage.add("Неверные учетные данные");
            return errorMessage;
        }
        if(Boolean.TRUE.equals(userAccount.getIsEmailVerified())){
            errorMessage.add("Email " + userAccount.getEmail() + " уже подтвержден");
        }
        if (!(userAccount.getStatus().equals(EUserStatus.NEW_USER)
                ||userAccount.getStatus().equals(EUserStatus.ACTIVE))){
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору");
        }
        EmailApprovedToken emailApprovedToken  = userAccount.getToken();
        if(emailApprovedToken!=null){
            LocalDateTime nextTokenTime = emailApprovedToken.getCreatedAt().plus(securityProperties.getApprovedEmailProperty().getPauseBetweenNextTokenGenerate(),
                    securityProperties.getApprovedEmailProperty().getUnit());
            if(nextTokenTime.isAfter(LocalDateTime.now())){
                errorMessage.add("Слишком частые запросы");
            }
        }
        if (!errorMessage.isEmpty()){
            return errorMessage;
        }
        String token = ApprovedTokenUtils.generateApprovedToken(securityProperties.getApprovedEmailProperty().getApprovedEmailTokenLength());
        LocalDateTime expired = LocalDateTime.now().plus(securityProperties.getApprovedEmailProperty().getApprovedEmailTokenLifetime(),
                securityProperties.getApprovedEmailProperty().getUnit());
        try {
            userEmailRepository.saveVerifiedEmailToken(userAccount.getId(),token,expired);
        }catch (RuntimeException exception){

        }
        EmailApprovedToken newToken = new EmailApprovedToken();
        newToken.setToken(token);
        newToken.setExpired(expired);
        newToken.setUserId(userAccount.getId());
        userAccount.setToken(newToken);
        eventMulticaster.multicastEvent(new UserEmailApprovedTokenEvent(userAccount));
        return errorMessage;
    }
    @Override
    @Transactional
    public List<String> approvedUserEmail(final String email, final String token) {
        UserAccountWithEmailApprovedToken userAccount = userEmailRepository.getUserAccountWithVerifiedToken(email);
        List<String> errorMessage = new ArrayList<>();
        if (userAccount==null){
            errorMessage.add("Неверные учетные данные");
            return errorMessage;
        }
        EmailApprovedToken emailApprovedToken  = userAccount.getToken();
        if(emailApprovedToken==null||!emailApprovedToken.getToken().equals(token)){
            errorMessage.add("Невалидный токен подтверждения");
        }
        if(Boolean.TRUE.equals(userAccount.getIsEmailVerified())){
            errorMessage.add("Email " + email + " уже подтвержден");
        }
        if (!(userAccount.getStatus().equals(EUserStatus.NEW_USER)
                ||userAccount.getStatus().equals(EUserStatus.ACTIVE))){
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору");
        }
        if (!errorMessage.isEmpty()){
            return errorMessage;
        }
        userEmailRepository.updateUserEmailStatusByUserId(userAccount.getId(),true);
        userEmailRepository.deleteUsedEmailApprovedTokenByUserId(userAccount.getId());
        eventMulticaster.multicastEvent(new UserAccountChangeEvent(email));
        return errorMessage;
    }

    
    @Override
    public boolean emailBusyCheck(final String email) {
        return userEmailRepository.countOfUsageEmail(email) > 0;
    }
}
