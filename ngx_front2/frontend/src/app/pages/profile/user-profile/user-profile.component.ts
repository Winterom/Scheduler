import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/api";
import {PasswordStrengthRequirement} from "../../../types/auth/PasswordStrengthRequirement";
import {ResponseStatus, WebsocketService} from "../../../services/ws/websocket";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ChangePswComponent} from "./change-psw/change-psw.component";
import {CustomMessage} from "../../../shared/messages/CustomMessages";
import {ChronosUtils} from "../../../shared/ChronosUtils";
import {EProfileWebsocketEvents} from "../EProfileWebsocketEvents";
import {UserService} from "../../../services/user.service";
import {UserInterfaces} from "../../../types/user/UserInterfaces";
import addErrorMessage = CustomMessage.addErrorMessage;
import addSuccessMessage = CustomMessage.addSuccessMessage;
import UserProfile = UserInterfaces.UserProfile;
import CheckEmailOrPhoneWSResponse = UserInterfaces.CheckEmailOrPhoneWSResponse;
import SendVerifyToken = UserInterfaces.SendVerifyToken;
import EUserStatus = UserInterfaces.EUserStatus;
import {interval, Observable, Subscription, takeUntil, timer} from "rxjs";



@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  providers: [MessageService,DialogService]
})
export class UserProfileComponent implements OnInit{
  userProfile:UserProfile  = new UserProfile();
  userForm: FormGroup;
  emailControl:FormControl;
  phoneControl:FormControl;
  sendEmailVerifyTokenLoaded:boolean=false;
  sendPhoneVerifyTokenLoaded:boolean = false;
  userFormLoading: boolean = false;
  userFormChanged: boolean = false;
  emailControlChanged:boolean=false;
  phoneControlChanged:boolean=false;
  pswFormRef: DynamicDialogRef|null=null;
  pswStrangeReq:PasswordStrengthRequirement|undefined;
  onError:EventEmitter<string>=new EventEmitter<string>();
  passwordExpired:string=''
  isCounterShow:boolean=false;


  constructor(public dialogService: DialogService,
              private wsService:WebsocketService,
              private userService:UserService,
              private messageService: MessageService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    });

  }
  ngOnInit(): void {
    this.wsService.on<PasswordStrengthRequirement>(EProfileWebsocketEvents.PASSWORD_STRENGTH)
      .subscribe({next:data=>{
          this.pswStrangeReq=data.data;
          this.passwordExpired = this.passwordExpiredToString();
        }})
    this.wsService.on<UserProfile>(EProfileWebsocketEvents.UPDATE_PROFILE).subscribe({next: data=>{
      this.userProfile = data.data;
      this.emailControl.setValue( this.userProfile.email);
      this.phoneControl.setValue(this.userProfile.phone);
      this.userFormLoading = false;
      if(this.userProfile.emailVerifyToken!==null){
        let finishDate = new Date(this.userProfile.emailVerifyToken.nextTokenAfter);
        if((finishDate.getTime()-Date.now())>0){
          this.isCounterShow=true;
          return;
        }else {
          this.isCounterShow=false;
        }
      }
      }})
    this.wsService.on<CheckEmailOrPhoneWSResponse>(EProfileWebsocketEvents.CHECK_PHONE_BUSY).subscribe({next: response=>{
        let rawPhone: string = this.phoneControl.value.toString();
        const phone = this.formatPhoneValue(rawPhone)
      if(response.data.isBusy&&response.data.param===phone){
          this.phoneControl.setErrors({phoneBusy:true});
          addErrorMessage(this.messageService,'Телефон: '+response.data.param+' занят другим аккаунтом','Проверка телефона')
          return;
        }
        if(!response.data.isBusy&&response.data.param===phone){
          this.phoneControl.updateValueAndValidity();
          addSuccessMessage(this.messageService,'Телефон введен правильно и свободен','Проверка телефона')
        }
      }})
    this.wsService.on<CheckEmailOrPhoneWSResponse>(EProfileWebsocketEvents.CHECK_EMAIL_BUSY).subscribe({next: response=>{
        if(response.data.isBusy&&response.data.param===this.emailControl.value){
          this.emailControl.setErrors({emailBusy:true});
          addErrorMessage(this.messageService,'Email: '+response.data.param+' занят другим аккаунтом','Проверка email')
          return;
        }
        if(!response.data.isBusy&&response.data.param===this.emailControl.value){
          this.emailControl.updateValueAndValidity();
          addSuccessMessage(this.messageService,'Email введен правильно и свободен','Проверка email')
        }
      }})
    this.wsService.on<SendVerifyToken>(EProfileWebsocketEvents.SEND_EMAIL_VERIFY_TOKEN).subscribe({next: data=>{
      if(data.responseStatus===ResponseStatus.OK){
          this.userProfile.emailVerifyToken = data.data;
          if(this.userProfile.emailVerifyToken!==null){
            let finishDate = new Date(this.userProfile.emailVerifyToken.nextTokenAfter);
            if((finishDate.getTime()-Date.now())>0){
              this.isCounterShow=true;
              return;
            }else {
              this.isCounterShow=false;
            }
          }
      }else {
        data.errorMessages.forEach(x=>addErrorMessage(this.messageService,x,null));
      }
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

    this.onError.subscribe({next:(value:string)=>{
      addErrorMessage(this.messageService,value,null);
      }})
  }
  submitUserFrm() {
    this.userFormLoading = true;
    let hasError = false;
    Object.keys(this.userForm.controls).forEach(key => {
      this.userForm.get(key)?.markAsTouched();
    });
    /***************************************************************************/
    if (this.emailControl.hasError('email')) {
      addErrorMessage(this.messageService,'Введен не корректный email', null)
      hasError = true;
    }
    if (this.emailControl.hasError('required')) {
      addErrorMessage(this.messageService,'Email не введен', null)
      hasError = true;
    }
    if (hasError){
      this.userFormLoading = false;
      return;
    }
    let rawPhone: string = this.phoneControl.value.toString();
    const phone = this.formatPhoneValue(rawPhone);
    this.wsService.send(EProfileWebsocketEvents.UPDATE_PROFILE,
      {id:this.userService.id,phone:phone,email:this.emailControl.value})
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
    this.wsService.send(EProfileWebsocketEvents.CHECK_PHONE_BUSY,{param:phone});
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
    this.wsService.send(EProfileWebsocketEvents.CHECK_EMAIL_BUSY,{param:email});
  }


  sendEmailVerifyToken() {
    this.wsService.send(EProfileWebsocketEvents.SEND_EMAIL_VERIFY_TOKEN,{param:null})
  }

  sendPhoneVerifyToken() {

  }
  changePasswordFormShow() {
    this.pswFormRef =  this.dialogService.open(ChangePswComponent,{
      header:'Изменить пароль',
      modal:true,
      width:"34rem",
      position:"top",
      draggable:true,
      resizable:false,
      data:{psw:this.pswStrangeReq,onError:this.onError}
    })
    this.pswFormRef.onClose.subscribe((message:string)=>{
      if (message){
        addSuccessMessage(this.messageService,message,'Изменение пароля')
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

  passwordExpiredToString():string{
    if(this.pswStrangeReq){
      const result = ChronosUtils.chronosToString(this.pswStrangeReq.unit,this.pswStrangeReq.passwordExpired);
      console.log(this.pswStrangeReq)
      if(result){
        return result;
      }
    }
    return '';
  }

  onCounterIsFinished($event: boolean) {
    this.isCounterShow=false;
  }


}

