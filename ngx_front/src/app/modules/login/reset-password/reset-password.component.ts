import { Component, OnInit } from '@angular/core';
import {InputDefinition} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";
import {CheckboxDefinition} from "../../../uikit/checkbox/CheckboxDefinition";
import {
  PasswordStrangeRequirement,
  passwordStrangeValidator
} from "../../../shared/validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../shared/validators/MatchingPasswordsValidator";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss','../login/login.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  public authTokenInput:InputDefinition = new InputDefinition();
  public codeInput:InputDefinition = new InputDefinition();
  public buttonCheckCodeDefinition:ButtonDefinition = new ButtonDefinition();
  public checkCodeForm : FormGroup;
  public passwordInput: InputDefinition= new InputDefinition();
  public confirmPasswordInput:InputDefinition = new InputDefinition();
  public checkboxDefinition: CheckboxDefinition = new CheckboxDefinition();
  constructor() {
    this.authTokenInput.label="Email или Телефон";
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.codeInput.label = "Введите код из почты или смс";
    this.codeInput.control = new FormControl<string>('',[Validators.required,
      Validators.pattern('[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')]);
    this.passwordInput.label ="Введите новый пароль";
    this.passwordInput.control = new FormControl<string>('',[Validators.required,
                                      passwordStrangeValidator(new PasswordStrangeRequirement())])
    this.confirmPasswordInput.label ="Повторите новый пароль";
    this.confirmPasswordInput.control = new FormControl<string>('',[Validators.required])
    this.buttonCheckCodeDefinition.label="Проверить";
    this.buttonCheckCodeDefinition.background ="#0E74B0";
    this.buttonCheckCodeDefinition.backgroundHover = "#387ca4";
    this.checkboxDefinition.label="Показать пароли"
    this.checkCodeForm = new FormGroup({
      'tokenInput':this.authTokenInput.control,
      'verifyCodeInput':this.codeInput.control,
      'passwordInput': this.passwordInput.control,
      'confirmPassword': this.confirmPasswordInput.control
    },[checkIfMatchingPasswords(this.passwordInput.control,this.confirmPasswordInput.control)])
  }

  ngOnInit(): void {
  }

  public submitCheckCodeForm() {
    console.log(this.checkCodeForm)
  }
}
