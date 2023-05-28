import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {MessageService} from "primeng/api";
import {AuthMessage} from "../../../messages/AuthMessages";
import {UserService} from "../../../services/user.service";
import addErrorMessage = AuthMessage.addErrorMessage;


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class LoginComponent {
  loading: boolean = false;
  public authForm : FormGroup;
  emailOrPhoneInput:FormControl;
  passwordInput:FormControl;
  constructor(private router: Router,
              private loginService:LoginService,
              private messageService: MessageService,
              private user:UserService
  ) {
    this.emailOrPhoneInput = new FormControl<string>('',[Validators.required]);
    this.passwordInput = new FormControl<string>('',Validators.required);
    this.authForm = new FormGroup<any>({
      "emailOrPhoneInput": this.emailOrPhoneInput,
      "pswInput": this.passwordInput
    })
  }
  login() {
    this.loading =true;
    Object.keys(this.authForm.controls).forEach(key => {
      this.authForm.get(key)?.markAsTouched();
    });
    if(this.emailOrPhoneInput.hasError('required')){
      addErrorMessage(this.messageService,'Введите телефон или email', null)

    }
    if(this.passwordInput.hasError('required')){
      addErrorMessage(this.messageService,'Введите пароль', null)
    }
    if(this.emailOrPhoneInput.invalid||this.passwordInput.invalid){
      this.loading=false;
      return;
    }

    this.loginService.login(this.emailOrPhoneInput.value,this.passwordInput.value)
      .subscribe({next:data=>{
          this.user.token=data.access_token;
          if(!this.user.isAuth){
            addErrorMessage(this.messageService,'Токен авторизации не валиден!',null)
            this.loading=false;
            return;
          }
          this.loading=false;
          this.router.navigate(['desktop']);
        }, error:err=>{
          this.loading=false;
          const errorMessages: string [] = err.error.messages
          errorMessages.forEach((value)=>{
            addErrorMessage(this.messageService,value,err.error.statusCode);
          })
          this.loading=false;
        }} )
  }

  selectRegisterPage(){
    this.router.navigate(['registration']);
  }
  selectRestorePage(){
    this.router.navigate(['password/reset']);
  }


}


