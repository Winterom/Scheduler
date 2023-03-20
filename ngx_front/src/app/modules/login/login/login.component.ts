import {Component, OnInit} from '@angular/core';
import {InputDefinition, InputType} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {AuthenticationAPI} from "../AuthenticationAPI";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {EventBusService} from "../../../shared/services/eventBus/event-bus.service";
import {AppEvents} from "../../../shared/services/eventBus/EventData";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public authTokenInput:InputDefinition = new InputDefinition();
  public passwordInput:InputDefinition = new InputDefinition();
  public buttonAuthDefinition:ButtonDefinition = new ButtonDefinition();
  public buttonRestorePswDefinition:ButtonDefinition = new ButtonDefinition();
  public checkboxDefinition:CheckboxDefinition = new CheckboxDefinition();
  public api:AuthenticationAPI = new AuthenticationAPI();
  public authForm : FormGroup;
  public resetPasswordForm:FormGroup;
  public showRestorePassword:boolean=false

  constructor(private eventBus:EventBusService) {
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.passwordInput.control = new FormControl<string>('',Validators.required);
    this.authTokenInput.label="Email или Телефон";
    this.passwordInput.label= "Пароль";
    this.passwordInput.type=InputType.PASSWORD;
    this.checkboxDefinition.label="Показать пароль";
    this.checkboxDefinition.onChange=()=>{
      this.clickCheckbox();
    }
    this.buttonAuthDefinition.label="Войти";
    this.buttonAuthDefinition.background ="#0E74B0";
    this.buttonAuthDefinition.backgroundHover = "#387ca4";

    this.buttonAuthDefinition.onClick=()=>{
      this.submitAuthForm();
    }
    this.buttonRestorePswDefinition.label="Отправить";
    this.buttonRestorePswDefinition.background ="#0E74B0";
    this.buttonRestorePswDefinition.backgroundHover = "#387ca4";

    this.authForm = new FormGroup<any>({
      "tokenInput": this.authTokenInput.control,
      "pswInput": this.passwordInput.control
    })
    this.resetPasswordForm = new FormGroup<any>({
      "tokenInput":this.authTokenInput.control
    })

  }

    ngOnInit(): void {

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
    clickCheckbox(){
      const id=this.passwordInput.id;
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }

    resetPasswordShow(){
      this.authTokenInput.control.reset();
      this.showRestorePassword=!this.showRestorePassword;
    }
}
