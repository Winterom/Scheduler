package alexey.grizly.com.users.controllers;


import alexey.grizly.com.users.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserAccountsController {
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountsController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @GetMapping("user/password/change/token")
    public ResponseEntity<?> sendTokenForResetPassword(){
        return null;
    }

    @PutMapping ("user/password/change")
    public ResponseEntity<?> changePassword(){
        return ResponseEntity.ok("Пароль изменен");
    }

}
