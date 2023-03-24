package users.auth;

import alexey.grizly.com.StartApplication;
import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.commons.security.EAuthorities;
import alexey.grizly.com.commons.security.EUserStatus;
import alexey.grizly.com.users.dto.request.AuthRequestDto;
import alexey.grizly.com.users.models.AppAuthorities;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {StartApplication.class})
@ExtendWith({SpringExtension.class})
public class AuthControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MockMvc mvc;
    @MockBean
    private AuthService authService;
    private final static AuthRequestDto authDto = new AuthRequestDto();
    private final static UserAccount userAccount = new UserAccount();
    private final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,"Неверные учетные данные пользователя");


    public AuthControllerTest(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
    void contextLoads() {
        Assertions.assertNotNull(mvc);
    }
    @Test
    public void authenticationValidDtoTest() throws Exception {
        when(authService.authentication(authDto)).thenReturn(userAccount);
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticationNotValidNullTokenDtoTest() throws Exception {
        authDto.setEmailOrPhone(null);
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }
    @Test
    public void authenticationNotValidMinLengthTokenDtoTest() throws Exception {
        authDto.setEmailOrPhone("dw");
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }
    @Test
    public void authenticationNotValidMaxLengthTokenDtoTest() throws Exception {
        authDto.setEmailOrPhone("dwklagj;lkhgaekh[eb,leab,eab[p,beapkaebp,aeLength51");
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }

    @Test
    public void authenticationNotValidNullPasswordDtoTest() throws Exception {
        authDto.setPassword(null);
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }

    @Test
    public void authenticationNotValidMinLengthPasswordDtoTest() throws Exception {
        authDto.setPassword("tr");
        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }
    @Test
    public void authenticationNotValidMaxLengthPasswordDtoTest() throws Exception {
        authDto.setPassword("dwklagj;lkhgaekh[eb,leab,eab[p,beapkaebp,ae,baep," +
                "51dwklagj;lkhgaekh[eb,leab,eab[p,beapkaebp,Length101");

        mvc.perform(post("/api/v1/auth")
                        .content(this.objectMapper.writeValueAsString(authDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(errorDto)));
    }

}
