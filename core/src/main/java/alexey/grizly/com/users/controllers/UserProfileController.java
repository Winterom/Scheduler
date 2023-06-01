package alexey.grizly.com.users.controllers;

import alexey.grizly.com.commons.exceptions.AppResponseErrorDto;
import alexey.grizly.com.users.dtos.response.UserProfileResponseDto;
import alexey.grizly.com.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<?> getUserProfile(Principal principal){
        if (principal==null){
            final AppResponseErrorDto errorDto = new AppResponseErrorDto(HttpStatus.UNAUTHORIZED,"");
            return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userProfileService.getProfileByEmail(principal.getName()));
    }
}
