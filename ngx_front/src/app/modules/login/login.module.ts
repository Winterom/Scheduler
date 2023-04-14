import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {UikitModule} from "../../uikit/uikit.module";
import {ReactiveFormsModule} from "@angular/forms";
import {ResetPasswordComponent} from './reset-password/reset-password.component';


@NgModule({
  declarations: [
    LoginComponent,
    ResetPasswordComponent
  ],
    imports: [
        CommonModule,
        UikitModule,
        ReactiveFormsModule
    ]
})
export class LoginModule { }
