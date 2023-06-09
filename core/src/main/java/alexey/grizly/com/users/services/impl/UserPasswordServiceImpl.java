package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.users.events.UserPasswordChangeCreateTokenEvent;
import alexey.grizly.com.properties.properties.GlobalProperties;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.PasswordChangeToken;
import alexey.grizly.com.users.models.user.UserAccountWithPasswordChangeToken;
import alexey.grizly.com.users.repositories.ChangePasswordTokenRepository;
import alexey.grizly.com.users.repositories.UserPasswordRepository;
import alexey.grizly.com.users.services.UserPasswordService;
import alexey.grizly.com.users.utils.ApprovedTokenUtils;
import alexey.grizly.com.users.validators.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    private final ApplicationEventMulticaster eventMulticaster;
    private final GlobalProperties globalProperties;
    private final SecurityProperties securityProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final UserPasswordRepository userPasswordRepository;


    @Autowired
    public UserPasswordServiceImpl(final ApplicationEventMulticaster eventMulticaster,
                                   final GlobalProperties globalProperties,
                                   final SecurityProperties securityProperties,
                                   final BCryptPasswordEncoder bCryptPasswordEncoder,
                                   final ChangePasswordTokenRepository changePasswordTokenRepository,
                                   final UserPasswordRepository userPasswordRepository
    ) {
        this.eventMulticaster = eventMulticaster;
        this.globalProperties = globalProperties;
        this.securityProperties = securityProperties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.changePasswordTokenRepository = changePasswordTokenRepository;
        this.userPasswordRepository = userPasswordRepository;

    }

    @Override
    public List<String> createChangePasswordToken(final String email){
        List<String> errorMessage = new ArrayList<>();
        UserAccountWithPasswordChangeToken userAccount = changePasswordTokenRepository.isUserAccountHaveToken(email);
        if(userAccount==null){
            errorMessage.add("Аккаунт с email "+email+" не существует");
            return errorMessage;
        }
        if (!(userAccount.getStatus().equals(EUserStatus.NEW_USER)
                ||userAccount.getStatus().equals(EUserStatus.ACTIVE))){
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору");
            return errorMessage;
        }
        LocalDateTime pauseBetweenNextToken = LocalDateTime.now()
                .plusSeconds(securityProperties.getRestorePasswordTokenProperty().getPauseBetweenNextTokenGenerate());
        PasswordChangeToken token = userAccount.getPasswordChangeToken();
        if(token!=null&&token.getCreatedAt().isBefore(pauseBetweenNextToken)){
            errorMessage.add("Слишком частые запросы");
            return errorMessage;
        }

        LocalDateTime expireTokenTime = LocalDateTime.now().plus(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLifetime(),
                securityProperties.getRestorePasswordTokenProperty().getUnit());
        String newToken = ApprovedTokenUtils.generateApprovedToken(securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLength());
        changePasswordTokenRepository.saveChangePasswordToken(userAccount.getId(),expireTokenTime,newToken);
        String url =globalProperties.getHost() + "password/restore?email=" +email+"&token="+ newToken;
        UserPasswordChangeCreateTokenEvent event = new UserPasswordChangeCreateTokenEvent(new UserPasswordChangeCreateTokenEvent.EventParam(email,url));
        eventMulticaster.multicastEvent(event);
        return errorMessage;
    }

    @Override
    public List<String> changePassword(final String email, final String password, String token){
        UserAccountWithPasswordChangeToken userAccount = changePasswordTokenRepository.isUserAccountHaveToken(email);
        List<String> errorMessage = new ArrayList<>();
        PasswordChangeToken passwordChangeToken = userAccount.getPasswordChangeToken();
        if(passwordChangeToken==null){
           errorMessage.add("Токен валидации устарел");
           return errorMessage;
        }
        if (!(userAccount.getStatus().equals(EUserStatus.NEW_USER)
                ||userAccount.getStatus().equals(EUserStatus.ACTIVE))){
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору");
        }
        if(!passwordChangeToken.getToken().equals(token)){
            errorMessage.add("Токен валидации устарел");
            return errorMessage;
        }
        if(bCryptPasswordEncoder.matches(password, userAccount.getPassword())){
            errorMessage.add("Новый пароль не должен совпадать со старым");
            return errorMessage;
        }
        String passwordHash = bCryptPasswordEncoder.encode(password);
        LocalDateTime credentialExpired = LocalDateTime.now().plus(securityProperties.getUserPasswordStrength().getPasswordExpired(),
                securityProperties.getUserPasswordStrength().getUnit());
        userPasswordRepository.updatePassword(userAccount.getId(),passwordHash,credentialExpired);
        changePasswordTokenRepository.deleteUsedToken(userAccount.getId());
        return errorMessage;
    }

    @Override
    public List<String> changePassword(String email, String notValidationPassword){
        PasswordValidator passwordValidator = new PasswordValidator(securityProperties);
        List<String> errorMessage = new ArrayList<>();
        if(!passwordValidator.isValid(notValidationPassword,null)){
            errorMessage.add("Пароль не валиден");
            return errorMessage;
        }
        LocalDateTime credentialExpired = LocalDateTime.now().plus(securityProperties.getUserPasswordStrength().getPasswordExpired(),
                securityProperties.getUserPasswordStrength().getUnit());
        int updated = userPasswordRepository.updatePassword(email,notValidationPassword,credentialExpired);
        if(updated!=1){
            errorMessage.add("Не удалось обновить пароль");
        }
        return errorMessage;
    }
}
