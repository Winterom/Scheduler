import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {checkIfMatchingPasswords} from "../../../services/validators/MatchingPasswordsValidator";
import {ChangePasswordService} from "./change-password.service";

import {passwordStrangeValidator} from "../../../services/validators/PasswordStrangeValidator";
import {MessageService} from "primeng/api";
import {LoginMessage} from "../../../messages/LoginMessages";


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class ChangePasswordComponent implements OnInit{
  loading:boolean=false;
  authForm : FormGroup;
  passwordControl:FormControl;
  confirmPasswordControl:FormControl;
  emailControl:FormControl;
  tokenControl:FormControl;
  pswStrangeReq:PasswordStrangeRequirement= new PasswordStrangeRequirement();
  constructor(private router: Router,
              private changePasswordService:ChangePasswordService,
              private messageService: MessageService,
              private activateRoute: ActivatedRoute
  ) {
    this.passwordControl = new FormControl<string>('',[Validators.required, passwordStrangeValidator(this.pswStrangeReq)]);
    this.confirmPasswordControl = new FormControl<string>('',Validators.required);
    this.emailControl = new FormControl<string>('',[Validators.required,Validators.email]);
    this.tokenControl = new FormControl<string>('',[Validators.required,Validators.pattern('[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')])
    this.authForm = new FormGroup<any>({
      "newPassword": this.passwordControl,
      "confirmPassword": this.confirmPasswordControl,
      "email":this.emailControl,
      "token":this.tokenControl
    },[checkIfMatchingPasswords(this.passwordControl,this.confirmPasswordControl)])
  }
  ngOnInit(): void {
    this.changePasswordService.getPasswordRequirements().subscribe({next:data=>{
      this.pswStrangeReq=data;
      }});
    const token = this.activateRoute.snapshot.queryParams['token'];
    if (token) {
      this.tokenControl.setValue(token)
    }
    const mail = this.activateRoute.snapshot.queryParams['mail'];
    if (mail) {
      this.emailControl.setValue(mail)
    }
  }
  submitFrm() {
    this.loading =true;
    Object.keys(this.authForm.controls).forEach(key => {
      this.authForm.get(key)?.markAsTouched();
    });
    /***************************************************************************/
    if (this.emailControl.hasError('email')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введен не корректный email';
      this.messageService.add(message);
    }
    if (this.emailControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введен не корректный email';
      this.messageService.add(message);
    }
    /***************************************************************************/
    if(this.tokenControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введите токен';
      this.messageService.add(message);
    }
    if(this.tokenControl.hasError('pattern')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введен не корректный токен';
      this.messageService.add(message);
    }
    /***************************************************************************/
    if(this.passwordControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введите пароль';
      this.messageService.add(message);
    }
    if(this.passwordControl.hasError('passwordStrange')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Пароль не соотвествует требованиям';
      this.messageService.add(message);
    }
    /***************************************************************************/
    if(this.confirmPasswordControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Повторите пароль';
      this.messageService.add(message);
    }
  }

}
