import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {checkIfMatchingPasswords} from "../../../../../validators/MatchingPasswordsValidator";
import {AuthMessage} from "../../../../../messages/AuthMessages";
import addErrorMessage = AuthMessage.addErrorMessage;
import {MessageService} from "primeng/api";
import {PasswordStrengthRequirement} from "../../../../../types/PasswordStrengthRequirement";
import {WebsocketService} from "../../../../../services/ws/websocket";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-change-psw',
  templateUrl: './change-psw.component.html',
})
export class ChangePswComponent implements OnInit{
  passwordControl: FormControl;
  confirmPasswordControl: FormControl;
  passwordChangeForm:FormGroup;
  loadingPasswordChangeForm: boolean=false;
  pswStrangeReq:PasswordStrengthRequirement|undefined;
  messageService:MessageService|null = null;
  constructor(
              private wsService:WebsocketService,
              private config:DynamicDialogConfig,
              private ref: DynamicDialogRef) {
    this.passwordControl = new FormControl<string>('', [Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('', Validators.required);
    this.passwordChangeForm = new FormGroup<any>({
      passwordControl: this.passwordControl,
      confirmPasswordControl: this.confirmPasswordControl
    },[checkIfMatchingPasswords(this.passwordControl, this.confirmPasswordControl)])
  }
  ngOnInit(): void {
    this.messageService = this.config.data.messageService;
    this.pswStrangeReq = this.config.data.psw;
  }
  submitPasswordFrm() {
    this.loadingPasswordChangeForm = true;
    let hasError = false;
    Object.keys(this.passwordChangeForm.controls).forEach(key => {
      this.passwordChangeForm.get(key)?.markAsTouched();
    });
    if (this.passwordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Введите пароль', null)
      hasError = true;
    }
    if (this.passwordControl.hasError('passwordStrange')) {
      addErrorMessage(this.messageService,'Пароль не соответствует требованиям', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.confirmPasswordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Повторите пароль', null)
      hasError = true;
    }
    if (this.passwordChangeForm.hasError('match_error')) {
      addErrorMessage(this.messageService,'Пароли не совпадают', null)
      hasError = true;
    }
    if (hasError) {
      this.loadingPasswordChangeForm = false;
      return;
    }
    const password:string = this.passwordControl.value;
    this.ref.close(password);
  }


}
