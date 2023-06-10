import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileComponent} from "./profile.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {EmployerProfileComponent} from "./employer-profile/employer-profile.component";
import {ChangePswComponent} from "./user-profile/change-psw/change-psw.component";
import {PanelModule} from "primeng/panel";
import {SharedModule} from "../../../shared/shared.module";
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {InputMaskModule} from "primeng/inputmask";
import {ButtonModule} from "primeng/button";
import {ToastModule} from "primeng/toast";
import {TableModule} from "primeng/table";
import {DialogModule} from "primeng/dialog";
import {PasswordModule} from "primeng/password";



@NgModule({
  declarations: [
    ProfileComponent,
    UserProfileComponent,
    EmployerProfileComponent,
    ChangePswComponent
  ],
  imports: [
    CommonModule,
    PanelModule,
    SharedModule,
    InputTextModule,
    ReactiveFormsModule,
    InputMaskModule,
    ButtonModule,
    ToastModule,
    TableModule,
    DialogModule,
    PasswordModule
  ]
})
export class ProfileModule { }
