package alexey.grizly.com.users.services.impl;

import alexey.grizly.com.commons.exceptions.PSQLErrorsTranslator;
import alexey.grizly.com.properties.dtos.security.responses.PasswordStrengthResponseDto;
import alexey.grizly.com.properties.properties.SecurityProperties;
import alexey.grizly.com.users.messages.response.CheckBusyPhoneOrEmail;
import alexey.grizly.com.users.messages.response.ResponseMessage;
import alexey.grizly.com.users.messages.response.SendVerifyToken;
import alexey.grizly.com.users.messages.response.UserProfileResponse;
import alexey.grizly.com.users.models.user.UserProfileWithRolesAndTokens;
import alexey.grizly.com.users.repositories.UserProfileRepository;
import alexey.grizly.com.users.services.UserEmailService;
import alexey.grizly.com.users.services.UserPasswordService;
import alexey.grizly.com.users.services.UserPhoneNumberService;
import alexey.grizly.com.users.services.UserProfileService;
import alexey.grizly.com.users.utils.DBExceptionUtils;
import alexey.grizly.com.users.validators.PhoneNumberValidator;
import alexey.grizly.com.users.ws_handlers.EWebsocketEvents;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserPasswordService userPasswordService;
    private final UserEmailService userEmailService;
    private final SecurityProperties securityProperties;
    private final PSQLErrorsTranslator psqlErrorsTranslator;
    private final UserPhoneNumberService userPhoneNumberService;


    @Autowired
    public UserProfileServiceImpl(final UserPasswordService userPasswordService,
                                  final UserEmailService userEmailService,
                                  final UserProfileRepository userProfileRepository,
                                  final SecurityProperties securityProperties,
                                  final PSQLErrorsTranslator psqlErrorsTranslator,
                                  final UserPhoneNumberService userPhoneNumberService) {
        this.userPasswordService = userPasswordService;
        this.userEmailService = userEmailService;
        this.userProfileRepository = userProfileRepository;
        this.securityProperties = securityProperties;
        this.psqlErrorsTranslator = psqlErrorsTranslator;
        this.userPhoneNumberService = userPhoneNumberService;
    }

    @Override
    @Transactional
    public ResponseMessage<UserProfileResponse> getProfileByEmail(final String email) {
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.isValid(email,null)){
            return new ResponseMessage<>(EWebsocketEvents.SEND_EMAIL_VERIFY_TOKEN,
                    List.of("Невалидный email"));
        }
        UserProfileWithRolesAndTokens userProfile = userProfileRepository.getUserAccountWithRolesAndTokens(email);
        if(userProfile==null){
            return new ResponseMessage<>(EWebsocketEvents.UPDATE_PROFILE,List.of("Пользователь с "+email+" не найден"));
        }
        UserProfileResponse userAccount = new UserProfileResponse(userProfile);
        if(userProfile.getCreateAtEmailToken()==null){
            return new ResponseMessage<>(EWebsocketEvents.UPDATE_PROFILE,userAccount, ResponseMessage.ResponseStatus.OK);
        }
        SendVerifyToken sendVerifyToken = new SendVerifyToken();
        LocalDateTime nextTokenTime = userProfile.getCreateAtEmailToken().plus(securityProperties.getApprovedEmailProperty().getPauseBetweenNextTokenGenerate()
                ,securityProperties.getApprovedEmailProperty().getUnit());
        System.out.println("nextTokenTime: "+nextTokenTime.toString());
        System.out.println("createdAt: "+userProfile.getCreateAtEmailToken().toString());
        sendVerifyToken.setNextTokenAfter(nextTokenTime.toInstant(ZonedDateTime.now().getOffset()).toEpochMilli());
        userAccount.setEmailVerifyToken(sendVerifyToken);
        return new ResponseMessage<>(EWebsocketEvents.UPDATE_PROFILE,userAccount, ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<PasswordStrengthResponseDto> getPasswordStrength() {
        SecurityProperties.UserPasswordStrength passwordStrength = securityProperties.getUserPasswordStrength();
        if(passwordStrength==null){
            return new ResponseMessage<>(EWebsocketEvents.PASSWORD_STRENGTH,List.of("Данные не доступны"));
        }
        return new ResponseMessage<>(EWebsocketEvents.PASSWORD_STRENGTH,
                new PasswordStrengthResponseDto(passwordStrength), ResponseMessage.ResponseStatus.OK);
    }

    @Override
    @Transactional
    public ResponseMessage<UserProfileResponse> updatePassword(final String email, final String password) {
        List<String> errorMessage = userPasswordService.changePassword(email,password);
        if(errorMessage.isEmpty()){
            return new ResponseMessage<>(EWebsocketEvents.UPDATE_PASSWORD,null, ResponseMessage.ResponseStatus.OK);
        }
        return new ResponseMessage<>(EWebsocketEvents.UPDATE_PASSWORD,errorMessage);
    }


    @Override
    @Transactional
    public ResponseMessage<UserProfileResponse> updateProfile(final String oldEmail, final String newEmail, final String phone) {
        List<String> errorMessage = new ArrayList<>();
        PhoneNumberValidator phoneNumberValidator =new PhoneNumberValidator();
        if(!phoneNumberValidator.isValid(phone,null)){
            errorMessage.add("Неверный формат телефона");
        }
        EmailValidator emailValidator = new EmailValidator();
        if (!oldEmail.equals(newEmail) && !emailValidator.isValid(newEmail, null)) {
            errorMessage.add("Не валидный email");
        }
        if(!errorMessage.isEmpty()){
            return this.createErrorResponseMessage(errorMessage);
        }
        ResponseMessage<UserProfileResponse> responseMessage = this.getProfileByEmail(oldEmail);
        LocalDateTime updatedAt = LocalDateTime.now();
        if(!oldEmail.equals(newEmail)
                &&!phone.equals(responseMessage.getPayload().getData().getPhone())){
            return this.updateEmailAndPhone(responseMessage,newEmail,phone,updatedAt);
        }
        if(oldEmail.equals(newEmail)
                &&!phone.equals(responseMessage.getPayload().getData().getPhone())){
            return this.updatePhone(responseMessage,phone,updatedAt);
        }
        if(!oldEmail.equals(newEmail)){
            return this.updateEmail(responseMessage,newEmail,updatedAt);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage<CheckBusyPhoneOrEmail> checkEmail(String email) {
        EmailValidator emailValidator = new EmailValidator();
        boolean resultCheck;
        if(emailValidator.isValid(email,null)){
           resultCheck = userEmailService.emailBusyCheck(email);
        }else {
            resultCheck = true;
        }
        CheckBusyPhoneOrEmail checkBusy = new CheckBusyPhoneOrEmail();
        checkBusy.setIsBusy(resultCheck);
        checkBusy.setParam(email);
        return new ResponseMessage<>(EWebsocketEvents.CHECK_EMAIL_BUSY, checkBusy, ResponseMessage.ResponseStatus.OK);
    }

    @Override
    public ResponseMessage<CheckBusyPhoneOrEmail> checkPhone(String phone) {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        boolean resultCheck;
        if(phoneNumberValidator.isValid(phone,null)){
           resultCheck = userPhoneNumberService.phoneBusyCheck(phone);
        }else {
            resultCheck = true;
        }
        CheckBusyPhoneOrEmail checkBusy = new CheckBusyPhoneOrEmail();
        checkBusy.setIsBusy(resultCheck);
        checkBusy.setParam(phone);
        return new ResponseMessage<>(EWebsocketEvents.CHECK_PHONE_BUSY, checkBusy, ResponseMessage.ResponseStatus.OK);
    }

    @Override
    @Transactional
    public ResponseMessage<SendVerifyToken> sendEmailVerifyToken(String email) {
        List<String> errorMessages = userEmailService.generateVerifiedEmailToken(email);
        if(!errorMessages.isEmpty()){
            return new ResponseMessage<>(EWebsocketEvents.SEND_EMAIL_VERIFY_TOKEN,
                    errorMessages);
        }
        SendVerifyToken sendVerifyToken = new SendVerifyToken();
        LocalDateTime nextTokenTime = LocalDateTime.now().plus(securityProperties.getApprovedEmailProperty().getPauseBetweenNextTokenGenerate(),
                securityProperties.getApprovedEmailProperty().getUnit());
        System.out.println("nextTokenTime2: "+nextTokenTime.toString());
        System.out.println("createdAt2: "+LocalDateTime.now().toString());
        ZoneId zoneId = ZoneId.systemDefault();
        sendVerifyToken.setNextTokenAfter(nextTokenTime.atZone(zoneId).toInstant().toEpochMilli());
        return new ResponseMessage<>(EWebsocketEvents.SEND_EMAIL_VERIFY_TOKEN,sendVerifyToken, ResponseMessage.ResponseStatus.OK);
    }

    private ResponseMessage<UserProfileResponse> updateEmailAndPhone(ResponseMessage<UserProfileResponse> responseMessage,
                                                                     String email, String phone,LocalDateTime updateAt){
        try {
            userProfileRepository.updateUserProfile(responseMessage.getPayload().getData().getId()
                    , email,phone,updateAt);
        }catch (RuntimeException exception) {
           return catchPsqlException(exception);
        }
        UserProfileResponse userProfile = responseMessage.getPayload().getData();
        userProfile.setEmail(email);
        userProfile.setPhone(phone);
        userProfile.setIsEmailVerified(false);
        userProfile.setIsPhoneVerified(false);
        userProfile.setUpdatedAt(updateAt);
        return responseMessage;
    }

    private ResponseMessage<UserProfileResponse> updatePhone(ResponseMessage<UserProfileResponse> responseMessage,
                                                                    String phone,LocalDateTime updateAt){
        try {
            userProfileRepository.updatePhone(responseMessage.getPayload().getData().getId(),
                    phone,updateAt);
        }catch (RuntimeException exception){
            return catchPsqlException(exception);
        }
        UserProfileResponse userProfile = responseMessage.getPayload().getData();
        userProfile.setPhone(phone);
        userProfile.setIsPhoneVerified(false);
        userProfile.setUpdatedAt(updateAt);
        return responseMessage;
    }

    private ResponseMessage<UserProfileResponse> updateEmail(ResponseMessage<UserProfileResponse> responseMessage,
                                                             String email,LocalDateTime updateAt){
        try {
            userProfileRepository.updateEmail(responseMessage.getPayload().getData().getId(),
                    email,updateAt);
        }catch (RuntimeException exception){
            return catchPsqlException(exception);
        }
        UserProfileResponse userProfile = responseMessage.getPayload().getData();
        userProfile.setEmail(email);
        userProfile.setIsEmailVerified(false);
        userProfile.setUpdatedAt(updateAt);
        return responseMessage;

    }

    private ResponseMessage<UserProfileResponse> createErrorResponseMessage(List<String> errorMessages){
        ResponseMessage<UserProfileResponse> responseErrorMessage = new ResponseMessage<>();
        ResponseMessage.MessagePayload<UserProfileResponse> errorPayload = new ResponseMessage.MessagePayload<>();
        responseErrorMessage.setEvent(EWebsocketEvents.UPDATE_PROFILE);
        errorPayload.setErrorMessages(errorMessages);
        errorPayload.setResponseStatus(ResponseMessage.ResponseStatus.ERROR);
        responseErrorMessage.setPayload(errorPayload);
        return responseErrorMessage;
    }

    private ResponseMessage<UserProfileResponse> catchPsqlException(RuntimeException exception){
        PSQLException psqlException = DBExceptionUtils.unwrapCause(PSQLException.class, exception);
        if (psqlException != null) {
            if (psqlException.getServerErrorMessage() != null) {
                String error = psqlErrorsTranslator.getMessage(psqlException.getServerErrorMessage());
                return createErrorResponseMessage(List.of(error));
            }else {
                log.error("");
                return createErrorResponseMessage(List.of(psqlException.getMessage()));
            }
        }else {
            throw exception;
        }
    }
}
