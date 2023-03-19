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
  public isSubmit:boolean=false;
  public showRestorePassword:boolean=false

  constructor(private eventBus:EventBusService) {
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator]);
    this.passwordInput.control = new FormControl<string>('',Validators.required);
    this.authForm = new FormGroup<any>({
      "tokenInput": this.authTokenInput.control,
      "pswInput": this.passwordInput.control
    })
    this.resetPasswordForm = new FormGroup<any>({
      "tokenInput":this.authTokenInput.control
    })
  }

    ngOnInit(): void {
    this.authTokenInput.label="Email или Телефон";
    this.authTokenInput.placeholder ="email или телефон";
    this.passwordInput.label= "Пароль";
    this.passwordInput.placeholder="Пароль";
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
      this.buttonRestorePswDefinition.onClick=()=>{
        this.submitRestorePswForm();
      }

  }
    submitAuthForm(){
      this.isSubmit=true;
      if(this.authForm.invalid){
        return;
      }
      this.api.auth_api;
    }
    submitRestorePswForm(){

    }
    clickCheckbox(){
      const id=this.passwordInput.id;
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }

    restorePasswordShow(){
      this.showRestorePassword=!this.showRestorePassword;
    }
}
