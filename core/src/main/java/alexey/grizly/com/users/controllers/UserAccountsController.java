package alexey.grizly.com.users.controllers;




import alexey.grizly.com.commons.entities.Paging;
import alexey.grizly.com.commons.errors.AppResponseErrorDto;
import alexey.grizly.com.commons.utils.url_query.UrlQueryParam;
import alexey.grizly.com.commons.utils.url_query.UrlResolver;
import alexey.grizly.com.commons.utils.url_query.UrlResolverImpl;
import alexey.grizly.com.users.models.UserAccount;
import alexey.grizly.com.users.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserAccountsController {
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountsController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllUserWithPagination(HttpServletRequest request){
        UrlResolver resolver = new UrlResolverImpl();
        UrlQueryParam param = resolver.resolveQueryString(request.getQueryString());
        Paging<UserAccount> userAccounts = userAccountService.getAllByPage(param);
        return ResponseEntity.ok(userAccounts);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<UserAccount> userAccount = userAccountService.getUserById(id);
        if(userAccount.isEmpty()){
            AppResponseErrorDto dto = new AppResponseErrorDto(HttpStatus.NOT_FOUND,"Не найден аккаунт с id: "+id);
            return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userAccount);
    }

    @GetMapping("user/password/change/token")
    public ResponseEntity<?> sendTokenForResetPassword(){
        return ResponseEntity.ok("Инструкция по сбросу пароля отправлена на почту");
    }

    @PutMapping ("user/password/change")
    public ResponseEntity<?> changePassword(){
        return ResponseEntity.ok("Пароль изменен");
    }

}
