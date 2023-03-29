import {Component, OnInit} from '@angular/core';
import {InputDefinition, InputType} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {
  passwordStrangeValidator
} from "../../../shared/validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../shared/validators/MatchingPasswordsValidator";
import {ButtonCheckCodeDefinition} from "./ButtonCheckCodeDefinition";
import {AppEvents} from "../../../services/eventBus/EventData";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {ActivatedRoute} from "@angular/router";
import {PasswordStrangeRequirement} from "./PasswordStrangeRequirement";
import {ResetPasswordService} from "../../../services/auth/reset-password.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss','../login/login.component.scss']
})
export class ResetPasswordComponent implements OnInit{
  public emailOrPhoneInput:InputDefinition = new InputDefinition('auth','Email или Телефон');
  public tokenInput:InputDefinition = new InputDefinition('code','Введите код из почты или смс');
  public buttonCheckCodeDefinition:ButtonDefinition = new ButtonCheckCodeDefinition();
  public checkCodeForm : FormGroup;
  public passwordInput: InputDefinition= new InputDefinition('password','Введите новый пароль');
  public confirmPasswordInput:InputDefinition = new InputDefinition('confirmPassword','Повторите новый пароль');
  public checkboxDefinition: CheckboxDefinition = new CheckboxDefinition('check','Показать пароли');
  public passwordStrangeRequirement:PasswordStrangeRequirement = new PasswordStrangeRequirement();


  constructor(private eventBus:EventBusService,
              private activateRoute: ActivatedRoute,
              private resetService:ResetPasswordService) {
    this.confirmPasswordInput.type=InputType.PASSWORD;
    this.passwordInput.type = InputType.PASSWORD;
    this.emailOrPhoneInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.tokenInput.control = new FormControl<string>('',[Validators.required,
      Validators.pattern('[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')]);
    this.passwordInput.control = new FormControl<string>('',[Validators.required,
                                      passwordStrangeValidator(this.passwordStrangeRequirement)]);
    this.confirmPasswordInput.control = new FormControl<string>('',[Validators.required]);
    this.checkboxDefinition.onChange=()=>{
      const id=[this.passwordInput.id,this.confirmPasswordInput.id];
      const state = this.checkboxDefinition.state;
      this.eventBus.emit({name:AppEvents.INPUT_SHOW_HIDE_PASSWORD,value:{state,id}});
    }
    this.checkCodeForm = new FormGroup({
      'tokenInput':this.emailOrPhoneInput.control,
      'verifyCodeInput':this.tokenInput.control,
      'passwordInput': this.passwordInput.control,
      'confirmPassword': this.confirmPasswordInput.control
    },[checkIfMatchingPasswords(this.passwordInput.control,this.confirmPasswordInput.control)])
  }


  public submitCheckCodeForm() {

  }

  ngOnInit(): void {
    const token = this.activateRoute.snapshot.queryParams['token'];
    if (token) {
     this.tokenInput.control.setValue(token)
    }
    const mail = this.activateRoute.snapshot.queryParams['mail'];
    if (mail) {
      this.emailOrPhoneInput.control.setValue(mail)
    }
    this.resetService.
      getPasswordRequirements()
        .subscribe((data)=>{
          this.passwordStrangeRequirement=data;
          console.log(this.passwordStrangeRequirement)
        })

  }

}
