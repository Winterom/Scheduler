import { NgModule } from '@angular/core';
import {CommonModule} from '@angular/common';
import { LoginComponent } from './login/login.component';
import { ResetComponent } from './reset/reset.component';
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {ButtonModule} from "primeng/button";
import {InputMaskModule} from "primeng/inputmask";
import {RegistrationComponent} from "./registration/registration.component";


@NgModule({
  declarations: [
    LoginComponent,
    ResetComponent,
    RegistrationComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    ReactiveFormsModule,
    PasswordModule,
    ButtonModule,
    InputMaskModule
  ]
})
export class LoginModule { }
