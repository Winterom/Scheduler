import {Component} from '@angular/core';
import {InputDefinition, InputType} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {AuthenticationAPI} from "../AuthenticationAPI";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {EventBusService} from "../../../shared/services/eventBus/event-bus.service";
import {AppEvents} from "../../../shared/services/eventBus/EventData";
import {LoginButtonDefinition} from "./LoginButtonDefinition";
import {ResetButtonDefinition} from "./ResetButtonDefinition";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  public authTokenInput:InputDefinition = new InputDefinition('ath','Email или Телефон');
  public passwordInput:InputDefinition = new InputDefinition('password','Пароль');
  public buttonAuthDefinition:ButtonDefinition = new LoginButtonDefinition();
  public buttonRestorePswDefinition:ButtonDefinition = new ResetButtonDefinition();
  public checkboxDefinition:CheckboxDefinition = new CheckboxDefinition('showPsw','Показать пароль');
  public api:AuthenticationAPI = new AuthenticationAPI();
  public authForm : FormGroup;
  public resetPasswordForm:FormGroup;
  public showRestorePassword:boolean=false

  constructor(private eventBus:EventBusService) {
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.passwordInput.control = new FormControl<string>('',Validators.required);
    this.passwordInput.type=InputType.PASSWORD;
    this.checkboxDefinition.onChange=()=>{
      this.clickShowPassword();
    }
    this.buttonAuthDefinition.onClick=()=>{
      this.submitAuthForm();
    }

    this.authForm = new FormGroup<any>({
      "tokenInput": this.authTokenInput.control,
      "pswInput": this.passwordInput.control
    })
    this.resetPasswordForm = new FormGroup<any>({
      "tokenInput":this.authTokenInput.control
    })

  }

    submitAuthForm(){
      console.log(this.authForm)
      if(this.authForm.invalid){
        return;
      }
      const token = this.authForm.controls['tokenInput'];
      console.log('token: '+token)
      if(token!=null){
        if(token.invalid){
          console.log("token invalid")
          return;
        }else {
          console.log("token valid")
          return;
        }
      }
      this.api.auth_api;
    }
    submitResetPswForm(){

    }
    clickShowPassword(){
      const id=this.passwordInput.id;
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }

    resetPasswordShow(){
      this.authTokenInput.control.reset();
      this.showRestorePassword=!this.showRestorePassword;
    }
}
