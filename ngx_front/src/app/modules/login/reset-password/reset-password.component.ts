import { Component, OnInit } from '@angular/core';
import {InputDefinition} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailOrPhoneValidator} from "../../../shared/validators/EmailOrPhoneValidator";

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
  constructor() {
    this.authTokenInput.label="Email или Телефон";
    this.authTokenInput.placeholder ="email или телефон";
    this.authTokenInput.control = new FormControl<string>('',[Validators.required,emailOrPhoneValidator()]);
    this.codeInput.label = "Введите код из почты или смс";
    this.codeInput.placeholder="Введите код из почты или смс";
    this.codeInput.control = new FormControl<string>('',[Validators.required,
      Validators.pattern('[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')]);
    this.buttonCheckCodeDefinition.label="Проверить";
    this.buttonCheckCodeDefinition.background ="#0E74B0";
    this.buttonCheckCodeDefinition.backgroundHover = "#387ca4";
    this.checkCodeForm = new FormGroup({
      'tokenInput':this.authTokenInput.control,
      'verifyCodeInput':this.codeInput.control
    })
  }

  ngOnInit(): void {
  }

  public submitCheckCodeForm() {
    console.log(this.checkCodeForm)
  }
}
