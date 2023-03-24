package users.auth;

import alexey.grizly.com.StartApplication;
import alexey.grizly.com.commons.security.EAuthorities;
import alexey.grizly.com.commons.security.EUserStatus;
import alexey.grizly.com.users.dto.request.AuthRequestDto;
import alexey.grizly.com.users.models.AppAuthorities;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.service.AuthService;
import alexey.grizly.com.users.service.UserAccountDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {StartApplication.class})
@ExtendWith({SpringExtension.class})
public class AuthServiceTest {
    private final static AuthRequestDto authDto = new AuthRequestDto();
    private final static UserAccount userAccount = new UserAccount();
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;
    @MockBean
    private UserAccountDetailsService userAccountDetailsService;

    @Autowired
    public AuthServiceTest(AuthService authService, BCryptPasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @BeforeAll
    public static void setup() {
        authDto.setPassword("2011");
        authDto.setEmailOrPhone("email1@one.ru");
        userAccount.setId(1L);
        userAccount.setEmail("email1@one.ru");
        userAccount.setPassword("$2y$10$jZM9lSaITCvsiqA3VY8LZOpOXASrk3aOHfVGsiJye0/m2.vGR7M2W");
        userAccount.setStatus(EUserStatus.ACTIVE);
        userAccount.setCredentialExpiredTime(LocalDateTime.now().plusHours(6));
        Set<AppAuthorities> authorities = new HashSet<>();
        AppAuthorities authority = new AppAuthorities();
        authority.setAuthorities(EAuthorities.GLOBAL_SETTINGS_READ);
        authorities.add(authority);
        userAccount.setAuthorities(authorities);
    }

    @Test
    public void correctAuthentication(){
        when(userAccountDetailsService.loadUserByUsername(authDto.getEmailOrPhone())).thenReturn(userAccount);
        UserDetails details= authService.authentication(authDto);
        assertThat(details.getUsername()).isEqualTo(authDto.getEmailOrPhone());
        assertTrue(encoder.matches(authDto.getPassword(),details.getPassword() ));
    }
    @Test
    public void inCorrectAuthentication(){
        userAccount.setPassword("$2y$10$SpW96iS3YUc6aBVhmIW6KuMC.i8r1dyxCISYtj51rF9uhJuKoZgWu");/*hash of "London"*/
        when(userAccountDetailsService.loadUserByUsername(authDto.getEmailOrPhone())).thenReturn(userAccount);
        assertThatThrownBy(()->authService.authentication(authDto)).isInstanceOf(BadCredentialsException.class);

    }
}
