package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserAccountRepository;
import alexey.grizly.com.users.services.RefreshTokenService;
import alexey.grizly.com.users.services.UserAccountService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;


@Service
public class UserAccountServiceImpl implements UserAccountService {
       private final SecurityProperties securityProperties;
       private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(final SecurityProperties securityProperties,
                                  final UserAccountRepository userAccountRepository) {
        this.securityProperties = securityProperties;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Nullable
    public Boolean generateAndSaveRestorePasswordToken(final Long userId, final LocalDateTime expireDate){
        String token = generateRestorePasswordToken();
        int count = userAccountRepository.saveRestorePasswordToken(userId,expireDate,token);
        return count == 1;
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
    public UserAccount registration() {
        return null;
    }

    private String generateRestorePasswordToken(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = this.securityProperties.getRestorePasswordTokenProperty().getRestorePasswordTokenLength();
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }
}
