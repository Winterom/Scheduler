import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {MessageService} from "primeng/api";
import {LoginMessage} from "../../../messages/LoginMessages";


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
  constructor(private router: Router,private loginService:LoginService,private messageService: MessageService) {
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
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введите телефон или email';
      this.messageService.add(message);
    }
    if(this.passwordInput.hasError('required')){
      let message = new LoginMessage.ErrorLoginMessage;
      message.detail='Введите пароль';
      this.messageService.add(message);
    }
    if(this.emailOrPhoneInput.invalid||this.passwordInput.invalid){
      this.loading=false;
      return;
    }

    this.loginService.login(this.emailOrPhoneInput.value,this.passwordInput.value)
      .subscribe({next:data=>{
          /*this.user.update(data);*/
          console.log(data);
          this.loading=false;
          this.messageService.add(new LoginMessage.SuccessLoginMessage)
          this.router.navigate(['desktop']);
        }, error:err=>{
          this.loading=false;
          let message = new LoginMessage.ErrorLoginMessage;
          message.detail=err.error.message;
          this.messageService.add(message);
          this.loading=false;
        }} )
  }

  selectRegisterPage(){
    this.router.navigate(['registration']);
  }
  selectRestorePage(){
    this.router.navigate(['restore']);
  }


}


