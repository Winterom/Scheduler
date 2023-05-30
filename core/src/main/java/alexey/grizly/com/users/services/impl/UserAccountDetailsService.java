package alexey.grizly.com.users.services.impl;


import alexey.grizly.com.users.repositories.UserDetailsRepository;
import alexey.grizly.com.users.validators.PhoneNumberValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserAccountDetailsService implements UserDetailsService {

   private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserAccountDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(final String authToken) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        EmailValidator emailValidator = new EmailValidator();
        if(emailValidator.isValid(authToken,null)){
            userDetails = loadByEmail(authToken);
        }
        PhoneNumberValidator phoneValidator = new PhoneNumberValidator();
        if(phoneValidator.isValid(authToken,null)){
            userDetails = loadByPhone(authToken);
        }
        if(userDetails==null){
            throw new UsernameNotFoundException("Неверные учетные данные пользователя");
        }
        return userDetails;
    }

    private UserDetails loadByEmail(final String email){
        return userDetailsRepository.loadByEmail(email);
    }
    private UserDetails loadByPhone(final String phone){
        return userDetailsRepository.loadByPhone(phone);
    }
}
