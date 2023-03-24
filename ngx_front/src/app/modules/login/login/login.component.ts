import {Component} from '@angular/core';
import {InputDefinition, InputType} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {AuthenticationAPI} from "../../../services/API/AuthenticationAPI";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {AppEvents} from "../../../services/eventBus/EventData";
import {LoginButtonDefinition} from "./LoginButtonDefinition";
import {ResetButtonDefinition} from "./ResetButtonDefinition";
import {AuthenticationService} from "../../../services/auth/authentication.service";
import {UserService} from "../../../services/auth/user.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  public emailOrPhoneInput:InputDefinition = new InputDefinition('ath','Email или Телефон');
  public passwordInput:InputDefinition = new InputDefinition('password','Пароль');
  public buttonAuthDefinition:ButtonDefinition = new LoginButtonDefinition();
  public buttonRestorePswDefinition:ButtonDefinition = new ResetButtonDefinition();
  public checkboxDefinition:CheckboxDefinition = new CheckboxDefinition('showPsw','Показать пароль');
  public api:AuthenticationAPI = new AuthenticationAPI();
  public authForm : FormGroup;
  public resetPasswordForm:FormGroup;
  public showRestorePassword:boolean=false
  public errorMessage:string|null=null;

  constructor(private eventBus:EventBusService,private authService:AuthenticationService,private user:UserService) {
    this.emailOrPhoneInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.passwordInput.control = new FormControl<string>('',Validators.required);
    this.passwordInput.type=InputType.PASSWORD;
    this.checkboxDefinition.onChange=()=>{
      this.clickShowPassword();
    }

    this.authForm = new FormGroup<any>({
      "emailOrPhoneInput": this.emailOrPhoneInput.control,
      "pswInput": this.passwordInput.control
    })
    this.resetPasswordForm = new FormGroup<any>({
      "emailOrPhoneInput":this.emailOrPhoneInput.control
    })

  }

    submitAuthForm(){
      Object.keys(this.authForm.controls).forEach(key => {
        this.authForm.get(key)?.markAsTouched();
      });
      if(this.authForm.invalid){
        return;
      }
      const emailOrPhone = this.authForm.controls['emailOrPhoneInput'];
      const password = this.authForm.controls['pswInput']
      if(emailOrPhone.invalid||password.invalid){
        return;
      }
      this.errorMessage=null;
      this.authService.login(emailOrPhone.value,password.value)
        .subscribe({next:data=>{
            this.user.update(data.access_token)
          }, error:data=>{
            console.log("error");
            console.log(data);
            this.errorMessage = data.message;
          }} )
    }
    submitResetPswForm(){

    }
    clickShowPassword(){
      const id=this.passwordInput.id;
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }

    resetPasswordShow(){
      this.emailOrPhoneInput.control.reset();
      this.showRestorePassword=!this.showRestorePassword;
    }
}
