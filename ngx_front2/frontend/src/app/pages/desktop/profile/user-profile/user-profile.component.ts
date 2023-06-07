import {Component, OnInit} from '@angular/core';
import {UserProfile} from "../../../../types/UserProfile";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfileService} from "../profile.service";
import {RegistrationService} from "../../../auth/registration/registration.service";
import {MessageService} from "primeng/api";
import {AuthMessage} from "../../../../messages/AuthMessages";
import {EUserStatus} from "../../../../types/EUserStatus";
import addSuccessMMessage = AuthMessage.addSuccessMMessage;
import addErrorMessage = AuthMessage.addErrorMessage;
import {PasswordStrengthRequirement} from "../../../../types/PasswordStrengthRequirement";
import {passwordStrangeValidator} from "../../../../validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../../validators/MatchingPasswordsValidator";
import {WSUserApi} from "../../../../services/API/WSUserApi";
import {WebsocketService} from "../../../../services/ws/websocket";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  providers: [MessageService]
})
export class UserProfileComponent implements OnInit{
  userStatusClasses:string='';
  userProfile:UserProfile  = new UserProfile();
  pswStrangeReq: PasswordStrengthRequirement = new PasswordStrengthRequirement;
  userApi:WSUserApi = new WSUserApi();
  userForm: FormGroup;
  passwordChangeForm:FormGroup;
  emailControl:FormControl;
  phoneControl:FormControl;
  passwordControl: FormControl;
  confirmPasswordControl: FormControl;
  sendEmailVerifyTokenLoaded:boolean=false;
  sendPhoneVerifyTokenLoaded:boolean = false;
  userFormLoading: boolean = false;
  userFormChanged: boolean = false;
  loadingPasswordChangeForm: boolean=false;
  emailControlChanged:boolean=false;
  phoneControlChanged:boolean=false;
  visibleChangePswForm: boolean=false;
  position: string="top";


  constructor(private profileService:ProfileService,
              private registrationService:RegistrationService,
              private wsService:WebsocketService,
              private messageService: MessageService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    });
    this.passwordControl = new FormControl<string>('', [Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('', Validators.required);
    this.passwordChangeForm = new FormGroup<any>({
      passwordControl: this.passwordControl,
      confirmPasswordControl: this.confirmPasswordControl
    },[checkIfMatchingPasswords(this.passwordControl, this.confirmPasswordControl)])
  }
  ngOnInit(): void {
    this.wsService.connect(this.userApi.getProfile);
    this.wsService.status.subscribe({next:value => {
        if(value){
          this.wsService.send('GET_PROFILE','');
          this.wsService.send('PASSWORD_STRENGTH','');
        }
      }})

    this.wsService.on<UserProfile>('PROFILE').subscribe({next:data=>{
      this.userProfile = data;
      this.emailControl.setValue( this.userProfile.email);
      this.phoneControl.setValue(this.userProfile.phone);
      }})
    this.wsService.on<PasswordStrengthRequirement>("PASSWORD_STRENGTH")
      .subscribe({next:data=>{
        this.pswStrangeReq=data;
        }})
    this.userForm.valueChanges.subscribe(()=>{
      const email:string = this.emailControl.value;
      const phone:string = this.formatPhoneValue(this.phoneControl.value);
      if(email===this.userProfile.email&&phone===this.userProfile.phone){
        this.userFormChanged=false;
        return;
      }
      this.userFormChanged=true;
    })
    this.phoneControl.valueChanges.subscribe(()=>{
      const email:string = this.emailControl.value;
      if(email===this.userProfile.email){
        this.emailControlChanged=false;
        return;
      }
      this.emailControlChanged=true;
    })
    this.phoneControl.valueChanges.subscribe(()=>{
      const phone:string = this.formatPhoneValue(this.phoneControl.value);
      if(phone===this.userProfile.phone){
        this.phoneControlChanged=false;
        return;
      }
      this.phoneControlChanged=true;
    })
  }
  submitUserFrm() {

  }

  checkPhone() {
    if (this.phoneControl.errors != null||this.phoneControl.value===0) {
      return;
    }
    let rawPhone: string = this.phoneControl.value.toString();
    const phone = this.formatPhoneValue(rawPhone)
    if(phone===this.userProfile.phone){
      this.phoneControlChanged=false
      return;
    }
    this.phoneControlChanged=true;
    this.registrationService.checkPhone(phone).subscribe({
      next: () => {
        addSuccessMMessage(this.messageService,'Телефон введен правильно и свободен',null)
        this.phoneControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.phoneControl.setErrors({phoneBusy:true});
      }
    })
  }

  checkEmail() {
    if (this.emailControl.errors != null) {
      return;
    }
    const email:string = this.emailControl.value;
    if(email===this.userProfile.email){
      this.emailControlChanged=false;
      return;
    }
    this.emailControlChanged=true;
    this.registrationService.checkEmail(email).subscribe({
      next: () => {
        addSuccessMMessage(this.messageService,'email введен правильно и свободен',null);
        this.emailControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.emailControl.setErrors({emailBusy:true});
      }
    })
  }


  sendEmailVerifyToken() {

  }

  sendPhoneVerifyToken() {

  }
  changePassword() {
    this.visibleChangePswForm=true;
    this.registrationService.getPasswordRequirements().subscribe({
      next: data => {
        this.pswStrangeReq = data;
        this.passwordControl.addValidators(passwordStrangeValidator(this.pswStrangeReq))
      }
    })
  }
  private formatPhoneValue(rawPhone:string):string{
    return String(rawPhone).replaceAll('-', '')
      .replace('(', '')
      .replace(')', '')
      .replace(' ', '');
  }

  public userStatus():string{
    return (<any>EUserStatus)[this.userProfile.status];
  }
  public statusClasses():string{
    switch (this.userProfile.status){
      case EUserStatus.DELETED: return 'text-purple-400';
      case EUserStatus.DISMISSED: return 'text-orange-400';
      case EUserStatus.BANNED: return 'text-red-600';
      case EUserStatus.ACTIVE: return 'text-green-400';
      case EUserStatus.NEW_USER: return 'text-blue-400';
      default: return 'text-green-400';
    }
  }


  submitPasswordFrm() {
    this.loadingPasswordChangeForm = true;
    let hasError = false;
    Object.keys(this.passwordChangeForm.controls).forEach(key => {
      this.passwordChangeForm.get(key)?.markAsTouched();
    });
    if (this.passwordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Введите пароль', null)
      hasError = true;
    }
    if (this.passwordControl.hasError('passwordStrange')) {
      addErrorMessage(this.messageService,'Пароль не соответствует требованиям', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.confirmPasswordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Повторите пароль', null)
      hasError = true;
    }
    if (this.passwordChangeForm.hasError('match_error')) {
      addErrorMessage(this.messageService,'Пароли не совпадают', null)
      hasError = true;
    }
    if (hasError) {
      this.loadingPasswordChangeForm = false;
      return;
    }
  }
}

