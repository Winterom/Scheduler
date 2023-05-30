package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.users.events.UserRegistrationEvent;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.repositories.UserRegistrationRepository;
import alexey.grizly.com.users.services.RoleService;
import alexey.grizly.com.users.services.UserEmailService;
import alexey.grizly.com.users.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final ApplicationEventMulticaster eventMulticaster;
    private final SecurityProperties securityProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserEmailService userEmailService;
    private final UserRegistrationRepository userRegistrationRepository;
    private final RoleService roleService;

    @Autowired
    public UserRegistrationServiceImpl(final ApplicationEventMulticaster eventMulticaster,
                                       final SecurityProperties securityProperties,
                                       final BCryptPasswordEncoder bCryptPasswordEncoder,
                                       final UserEmailService userEmailService,
                                       final UserRegistrationRepository userRegistrationRepository,
                                       final RoleService roleService) {
        this.eventMulticaster = eventMulticaster;
        this.securityProperties = securityProperties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userEmailService = userEmailService;
        this.userRegistrationRepository = userRegistrationRepository;
        this.roleService = roleService;
    }

    @Override
    public void createNewUserAccount(final String email,
                                     final String phone,
                                     final String password,
                                     final String name,
                                     final String surname,
                                     final String lastname) {
        String passwordHash = bCryptPasswordEncoder.encode(password);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime credentialExpired = createdAt.plus(securityProperties.getPasswordProperty().getPasswordExpired(),
                securityProperties.getPasswordProperty().getUnit());
        Long newUserId = userRegistrationRepository.saveNewUser(email,phone,passwordHash,credentialExpired, EUserStatus.NEW_USER,createdAt);
        roleService.setDefaultRoleForNewUser(newUserId);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(newUserId);
        userAccount.setEmail(email);
        userAccount.setPhone(phone);
        userAccount.setStatus(EUserStatus.NEW_USER);
        userAccount.setCredentialExpiredTime(credentialExpired);
        userAccount.setCreatedAt(createdAt);
        userEmailService.generateVerifiedEmailToken(newUserId);
        UserRegistrationEvent event = new UserRegistrationEvent(userAccount);
        eventMulticaster.multicastEvent(event);
    }
}
