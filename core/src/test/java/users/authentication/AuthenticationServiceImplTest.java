package users.authentication;

import alexey.grizly.com.StartApplication;
import alexey.grizly.com.commons.security.EAuthorities;
import alexey.grizly.com.users.dtos.request.AuthRequestDto;
import alexey.grizly.com.users.models.AppAuthorities;
import alexey.grizly.com.users.models.EUserStatus;
import alexey.grizly.com.users.models.user.UserAccount;
import alexey.grizly.com.users.services.impl.AuthenticationServiceImpl;
import alexey.grizly.com.users.services.impl.UserAccountDetailsService;
import alexey.grizly.com.users.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {StartApplication.class})
@ExtendWith({SpringExtension.class})
public class AuthenticationServiceImplTest {
    private final static AuthRequestDto authDto = new AuthRequestDto();
    private final static UserAccount userAccount = new UserAccount();
    private final AuthenticationServiceImpl authServiceImpl;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;
    @MockBean
    private UserAccountDetailsService userAccountDetailsService;
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Autowired
    public AuthenticationServiceImplTest(AuthenticationServiceImpl authServiceImpl, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder encoder) {
        this.authServiceImpl = authServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
        this.encoder = encoder;
    }

    @BeforeAll
    public static void setup() {
        authDto.setPassword("2011");
        authDto.setEmailOrPhone("email1@one.ru");
        userAccount.setId(1L);
        userAccount.setEmail("email1@one.ru");
        userAccount.setStatus(EUserStatus.ACTIVE);
        userAccount.setCredentialExpiredTime(LocalDateTime.now().plusHours(6));
        Set<AppAuthorities> authorities = new HashSet<>();
        AppAuthorities authority = new AppAuthorities();
        authority.setAuthorities(EAuthorities.GLOBAL_SETTINGS_READ);
        authorities.add(authority);
        userAccount.setAuthorities(authorities);
    }

    @Test
     void correctAuthentication(){
        userAccount.setPassword("$2y$10$jZM9lSaITCvsiqA3VY8LZOpOXASrk3aOHfVGsiJye0/m2.vGR7M2W");
        when(userAccountDetailsService.loadUserByUsername(authDto.getEmailOrPhone())).thenReturn(userAccount);
        String token = authServiceImpl.authentication(authDto.getEmailOrPhone(),authDto.getPassword(),response);
        assertThat(this.jwtTokenUtil.getEmailFromToken(token)).isEqualTo(authDto.getEmailOrPhone());
        assertThat(this.jwtTokenUtil.getAuthorities(token)).isEqualTo(userAccount.getAuthorities().stream().map(AppAuthorities::getAuthority).toList());
    }
    @Test
     void inCorrectAuthentication(){
        userAccount.setPassword("$2y$10$SpW96iS3YUc6aBVhmIW6KuMC.i8r1dyxCISYtj51rF9uhJuKoZgWu");/*hash of "London"*/
        when(userAccountDetailsService.loadUserByUsername(authDto.getEmailOrPhone())).thenReturn(userAccount);
        assertThatThrownBy(()-> authServiceImpl.authentication(authDto.getEmailOrPhone(),authDto.getPassword(),response)).isInstanceOf(BadCredentialsException.class);

    }
}
