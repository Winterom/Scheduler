import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "./login.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../login/login.scss']
})
export class LoginComponent {
  loading: boolean = false;
  public authForm : FormGroup;
  emailOrPhoneInput:FormControl;
  passwordInput:FormControl;
  constructor(private router: Router,private loginService:LoginService,) {
    this.emailOrPhoneInput = new FormControl<string>('',[Validators.required]);
    this.passwordInput = new FormControl<string>('',Validators.required);
    this.authForm = new FormGroup<any>({
      "emailOrPhoneInput": this.emailOrPhoneInput,
      "pswInput": this.passwordInput
    })
  }
  login() {
    Object.keys(this.authForm.controls).forEach(key => {
      this.authForm.get(key)?.markAsTouched();
    });

  }

  selectRegisterPage(){
    this.router.navigate(['registration']);
  }
  selectRestorePage(){
    this.router.navigate(['reset']);
  }


}


