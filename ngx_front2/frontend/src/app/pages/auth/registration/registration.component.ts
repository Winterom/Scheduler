import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RegistrationService} from "./registration.service";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {passwordStrangeValidator} from "../../../validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../validators/MatchingPasswordsValidator";
import {LoginMessage} from "../../../messages/LoginMessages";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class RegistrationComponent implements OnInit{
  loading: boolean = false;
  pswStrangeReq:PasswordStrangeRequirement = new PasswordStrangeRequirement;
  registrationForm:FormGroup;
  passwordControl:FormControl;
  confirmPasswordControl:FormControl;
  emailControl:FormControl;
  nameControl:FormControl;
  surnameControl:FormControl;
  lastnameControl:FormControl;
  phoneControl:FormControl;
  constructor(private router: Router,
              private messageService: MessageService,
              private regService:RegistrationService) {
    this.passwordControl = new FormControl<string>('',[Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('',Validators.required);
    this.emailControl = new FormControl<string>('',[Validators.required,Validators.email]);
    this.nameControl = new FormControl<string>('',[Validators.required,Validators.pattern(/[А-Яа-яЁё]/)]);
    this.surnameControl = new FormControl<string>('',[Validators.required,Validators.pattern(/[А-Яа-яЁё]/)]);
    this.lastnameControl = new FormControl<string>('',[Validators.pattern(/[А-Яа-яЁё]/)]);
    this.phoneControl = new FormControl<number>(0);
    this.registrationForm = new FormGroup<any>({
      "newPassword": this.passwordControl,
      "confirmPassword": this.confirmPasswordControl,
      "email":this.emailControl,
      "name":this.nameControl,
      "surname":this.surnameControl,
      "lastname":this.lastnameControl,
      "phone":this.phoneControl
    },[checkIfMatchingPasswords(this.passwordControl,this.confirmPasswordControl)])
  }

  ngOnInit(): void {
    this.regService.getPasswordRequirements().
      subscribe({next:data=>{
      this.pswStrangeReq=data;
      this.passwordControl.addValidators(passwordStrangeValidator(this.pswStrangeReq))
      }})
  }
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {
    this.loading =true;
    let hasError=false;
    Object.keys(this.registrationForm.controls).forEach(key => {
      this.registrationForm.get(key)?.markAsTouched();
    });
    /***************************************************************************/
    if (this.emailControl.hasError('email')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введен не корректный email';
      this.messageService.add(message);
      hasError=true;
    }
    if (this.emailControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Email не введен';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.surnameControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Не введена фамилия пользователя';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.surnameControl.hasError('pattern')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Недопустимые символы в фамилии пользователя';
      this.messageService.add(message);
      hasError=true;
    }

    /***************************************************************************/
    if(this.nameControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Не введено имя пользователя';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.nameControl.hasError('pattern')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Недопустимые символы в имени пользователя';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.lastnameControl.hasError('pattern')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Недопустимые символы в отчестве пользователя';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.passwordControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введите пароль';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.passwordControl.hasError('passwordStrange')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Пароль не соответствует требованиям';
      this.messageService.add(message);
      hasError=true;
    }
    /***************************************************************************/
    if(this.confirmPasswordControl.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Повторите пароль';
      this.messageService.add(message);
      hasError=true;
    }
    if(this.registrationForm.hasError('match_error')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Пароли не совпадают';
      this.messageService.add(message);
      hasError=true;
    }
    if(hasError){
      this.loading=false;
      return;
    }
  }


}
