import {Component, OnInit} from '@angular/core';
import {InputDefinition, InputType} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {
  PasswordStrangeRequirement,
  passwordStrangeValidator
} from "../../../shared/validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../shared/validators/MatchingPasswordsValidator";
import {ButtonCheckCodeDefinition} from "./ButtonCheckCodeDefinition";
import {AppEvents} from "../../../shared/services/eventBus/EventData";
import {EventBusService} from "../../../shared/services/eventBus/event-bus.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss','../login/login.component.scss']
})
export class ResetPasswordComponent implements OnInit{
  public authTokenInput:InputDefinition = new InputDefinition('auth','Email или Телефон');
  public codeInput:InputDefinition = new InputDefinition('code','Введите код из почты или смс');
  public buttonCheckCodeDefinition:ButtonDefinition = new ButtonCheckCodeDefinition();
  public checkCodeForm : FormGroup;
  public passwordInput: InputDefinition= new InputDefinition('password','Введите новый пароль');
  public confirmPasswordInput:InputDefinition = new InputDefinition('confirmPassword','Повторите новый пароль');
  public checkboxDefinition: CheckboxDefinition = new CheckboxDefinition('check','Показать пароли');


  constructor(private eventBus:EventBusService,private activateRoute: ActivatedRoute) {

    this.confirmPasswordInput.type=InputType.PASSWORD;
    this.passwordInput.type = InputType.PASSWORD;
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.codeInput.control = new FormControl<string>('',[Validators.required,
      Validators.pattern('[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')]);
    this.passwordInput.control = new FormControl<string>('',[Validators.required,
                                      passwordStrangeValidator(new PasswordStrangeRequirement())]);
    this.confirmPasswordInput.control = new FormControl<string>('',[Validators.required]);
    this.checkboxDefinition.onChange=()=>{
      const id=[this.passwordInput.id,this.confirmPasswordInput.id];
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }
    this.checkCodeForm = new FormGroup({
      'tokenInput':this.authTokenInput.control,
      'verifyCodeInput':this.codeInput.control,
      'passwordInput': this.passwordInput.control,
      'confirmPassword': this.confirmPasswordInput.control
    },[checkIfMatchingPasswords(this.passwordInput.control,this.confirmPasswordInput.control)])
  }


  public submitCheckCodeForm() {

  }

  ngOnInit(): void {
    const rawParams = this.activateRoute.snapshot.params['token'];
    const params = Buffer.from(rawParams,'base64').toLocaleString().split("||");
    params.length
  }
}
