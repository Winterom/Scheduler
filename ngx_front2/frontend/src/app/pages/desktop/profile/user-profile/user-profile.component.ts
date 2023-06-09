import {Component, OnInit} from '@angular/core';
import {UserProfile} from "../../../../types/UserProfile";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/api";
import {EUserStatus} from "../../../../types/EUserStatus";
import {PasswordStrengthRequirement} from "../../../../types/PasswordStrengthRequirement";
import {WSUserApi} from "../../../../services/API/WSUserApi";
import {WebsocketService} from "../../../../services/ws/websocket";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ChangePswComponent} from "./change-psw/change-psw.component";
import {WSRequestEvents} from "../../../../types/WSRequestEvents";
import {WSResponseEvents} from "../../../../types/WSResponseEvents";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  providers: [MessageService,DialogService]
})
export class UserProfileComponent implements OnInit{
  userStatusClasses:string='';
  userProfile:UserProfile  = new UserProfile();
  userApi:WSUserApi = new WSUserApi();
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

  constructor(public dialogService: DialogService,
              private wsService:WebsocketService,
              private messageService: MessageService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    });

  }
  ngOnInit(): void {
    this.wsService.connect(this.userApi.getProfile);
    this.wsService.status.subscribe({next:value => {
        if(value){
          this.wsService.send(WSRequestEvents.GET_PROFILE,'');
          this.wsService.send(WSRequestEvents.PASSWORD_STRENGTH,'');
        }
      }})
    this.wsService.on<PasswordStrengthRequirement>(WSResponseEvents.PASSWORD_STRENGTH)
      .subscribe({next:data=>{
          this.pswStrangeReq=data.data;
        }})
    this.wsService.on<UserProfile>(WSResponseEvents.UPDATE_PROFILE).subscribe({next:data=>{
      this.userProfile = data.data;
      this.emailControl.setValue( this.userProfile.email);
      this.phoneControl.setValue(this.userProfile.phone);
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
    this.pswFormRef?.onClose.subscribe((password:string)=>{
      console.log(password)
      if(password){
        this.wsService.send(WSRequestEvents.UPDATE_PASSWORD,password);
      }
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
  }


  sendEmailVerifyToken() {

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
      data:{psw:this.pswStrangeReq,messageService:this.messageService}
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



}

