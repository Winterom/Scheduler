import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordStrengthRequirement} from "../../../types/auth/PasswordStrengthRequirement";
import {checkIfMatchingPasswords} from "../../../validators/MatchingPasswordsValidator";
import {ChangePasswordService} from "./change-password.service";
import {passwordStrangeValidator} from "../../../validators/PasswordStrangeValidator";
import {MessageService} from "primeng/api";
import {CustomMessage} from "../../../shared/messages/CustomMessages";
import addErrorMessage = CustomMessage.addErrorMessage;


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class ChangePasswordComponent implements OnInit{
  loading:boolean=false;
  changePasswordForm : FormGroup;
  passwordControl:FormControl;
  confirmPasswordControl:FormControl;
  emailControl:FormControl;
  tokenControl:FormControl;
  state: 'change'|'complete'= 'change';
  pswStrangeReq:PasswordStrengthRequirement|undefined;
  constructor(private router: Router,
              private changePasswordService:ChangePasswordService,
              private messageService: MessageService,
              private activateRoute: ActivatedRoute
  ) {
    this.passwordControl = new FormControl<string>('',[Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('',Validators.required);
    this.emailControl = new FormControl<string>('',[Validators.required,Validators.email]);
    this.tokenControl = new FormControl<string>('',[Validators.required,Validators.max(100)]);
    this.changePasswordForm = new FormGroup<any>({
      "newPassword": this.passwordControl,
      "confirmPassword": this.confirmPasswordControl,
      "email":this.emailControl,
      "token":this.tokenControl
    },[checkIfMatchingPasswords(this.passwordControl,this.confirmPasswordControl)])
  }
  ngOnInit(): void {
    this.changePasswordService.getPasswordRequirements().subscribe({next:data=>{
      this.pswStrangeReq=data;
      this.passwordControl.addValidators(passwordStrangeValidator(this.pswStrangeReq))
      }});
    const token = this.activateRoute.snapshot.queryParams['token'];
    if (token) {
      this.tokenControl.setValue(token);
    }
    const email = this.activateRoute.snapshot.queryParams['email'];
    if (email) {
      this.emailControl.setValue(email);
    }
  }
  submitFrm() {
    this.loading =true;
    let hasError=false;
    Object.keys(this.changePasswordForm.controls).forEach(key => {
      this.changePasswordForm.get(key)?.markAsTouched();
    });
    /***************************************************************************/
    if (this.emailControl.hasError('email')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Введен не корректный email';
      this.messageService.add(message);
      hasError=true;
    }
    if (this.emailControl.hasError('required')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Email не введен';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.tokenControl.hasError('required')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Введите токен';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.tokenControl.hasError('pattern')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Введен не корректный токен';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.passwordControl.hasError('required')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Введите пароль';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.passwordControl.hasError('passwordStrange')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Пароль не соответствует требованиям';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.confirmPasswordControl.hasError('required')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Повторите пароль';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.changePasswordForm.hasError('match_error')){
      let message = new CustomMessage.ErrorMessage;
      message.detail='Пароли не совпадают';
      this.messageService.add(message);
      hasError=true;
    }
    if(hasError){
      this.loading=false;
      return;
    }
    this.changePasswordService.putUpdatePassword(this.emailControl.value,
      this.tokenControl.value,
      this.passwordControl.value).subscribe({next:data=>{
          this.state='complete';
          this.loading=false;
        },error:err=>{
        console.log(err)
          addErrorMessage(this.messageService,err.error.messages,null)
          this.loading=false;
      }
      })
  }

  returnToAuth() {
    this.router.navigate(['login'])
  }
}
