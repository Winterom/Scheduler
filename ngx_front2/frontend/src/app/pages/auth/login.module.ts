import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RestoreComponent} from './restore-password/restore.component';
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {ButtonModule} from "primeng/button";
import {InputMaskModule} from "primeng/inputmask";
import {RegistrationComponent} from "./registration/registration.component";
import {ToastModule} from "primeng/toast";
import {MessagesModule} from "primeng/messages";
import {ChangePasswordComponent} from './change-password/change-password.component';
import {InputTextareaModule} from "primeng/inputtextarea";
import {CardModule} from "primeng/card";
import {ApprovedEmailComponent} from './approved-email/approved-email.component';


@NgModule({
  declarations: [
    LoginComponent,
    RestoreComponent,
    RegistrationComponent,
    ChangePasswordComponent,
    ApprovedEmailComponent
  ],
    imports: [
        CommonModule,
        InputTextModule,
        ReactiveFormsModule,
        PasswordModule,
        ButtonModule,
        InputMaskModule,
        ToastModule,
        MessagesModule,
        InputTextareaModule,
        CardModule
    ]
})
export class LoginModule { }
