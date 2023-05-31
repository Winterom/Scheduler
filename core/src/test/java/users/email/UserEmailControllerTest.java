package users.email;

import alexey.grizly.com.StartApplication;

import alexey.grizly.com.users.dtos.request.ApprovedEmailRequestDto;
import alexey.grizly.com.users.services.impl.UserEmailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = {StartApplication.class})
@ExtendWith({SpringExtension.class})
public class UserEmailControllerTest {
    private final MockMvc mvc;
    @MockBean
    private UserEmailServiceImpl userEmailService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    final static String emailGood = "email1@one.ru";
    final static String emailBad1 = "email1@";
    final static String emailBad2 = "email1";
    final static ApprovedEmailRequestDto approvedEmailRequestDto = new ApprovedEmailRequestDto();

    public UserEmailControllerTest(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void approvedEmailValidateEmail() throws Exception {
        when(userEmailService.emailBusyCheck(emailGood)).thenReturn(false);
        String token = "qrJ9x3eXiqN4JiRZRMgFB9EDQd1G3U8fWCDdA2dz";
        when(userEmailService.approvedUserEmail(emailGood,token)).thenReturn(Collections.emptyList());
        emailBusyCheckValidationTrue();
        emailBusyCheckValidationFalse(emailBad2);
        emailBusyCheckValidationFalse(emailBad1);
        when(userEmailService.emailBusyCheck(emailGood)).thenReturn(true);
        emailBusyWhenEmailIsBusy();
    }

    void emailBusyCheckValidationTrue() throws Exception {
        mvc.perform(get("/api/v1/users/email/check/"+emailGood))
                .andExpect(status().isOk());
    }

    void emailBusyCheckValidationFalse(String email) throws Exception {
        mvc.perform(get("/api/v1/users/email/check/"+email))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
    void emailBusyWhenEmailIsBusy() throws Exception {
        mvc.perform(get("/api/v1/users/email/check/"+emailGood))
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }
    @Test
    void testEmailApprovedValidateEmailInDto() throws Exception {
        String token = "qrJ9x3eXiqN4JiRZRMgFB9EDQd1G3U8fWCDdA2dz";
        approvedEmailRequestDto.setEmail(emailGood);
        approvedEmailRequestDto.setToken(token);
        when(userEmailService.approvedUserEmail(emailGood,token)).thenReturn(Collections.emptyList());
        approvedEmailValidateDtoOk();
        approvedEmailRequestDto.setEmail(emailBad1);
        approvedEmailValidateDtoErrorEmail();
        approvedEmailRequestDto.setEmail(emailBad2);
        approvedEmailValidateDtoErrorEmail();
    }

    void approvedEmailValidateDtoOk() throws Exception {
        mvc.perform(put("/api/v1/users/email/approved")
                        .content(objectMapper.writeValueAsString(approvedEmailRequestDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
                .andExpect(status().isOk());
    }

    void approvedEmailValidateDtoErrorEmail() throws Exception {
        mvc.perform(put("/api/v1/users/email/approved")
                        .content(objectMapper.writeValueAsString(approvedEmailRequestDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.messages").value("Не валидный email"));
    }
    @Test
    void testEmailApprovedValidateTokenInDto() throws Exception {
        String token = "qrJ9x3eXiqN4JiRZRMgFB9EDQd1G3U8fWCDdA2d";/* длина меньше чем в параметрах*/
        approvedEmailRequestDto.setEmail(emailGood);
        approvedEmailRequestDto.setToken(token);
        approvedEmailValidateDtoErrorToken();
        approvedEmailRequestDto.setToken(null);
        approvedEmailValidateDtoErrorToken();
        approvedEmailRequestDto.setToken("qrJ9x3eXiqN4JiRZRMgFB9EDQd1G3U8fWCDdA2dB9EDQd1G3U8fWCDdA2d");
        approvedEmailValidateDtoErrorToken();
    }
    void approvedEmailValidateDtoErrorToken() throws Exception {
        mvc.perform(put("/api/v1/users/email/approved")
                        .content(objectMapper.writeValueAsString(approvedEmailRequestDto))
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.messages").value("Невалидный токен подтверждения"));
    }
}
