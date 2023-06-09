import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DesktopComponent} from './desktop.component';
import {PanelModule} from "primeng/panel";
import {ProfileComponent} from "./profile/profile.component";
import {SharedModule} from "../../shared/shared.module";
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {InputMaskModule} from "primeng/inputmask";
import {ButtonModule} from "primeng/button";
import {ToastModule} from "primeng/toast";
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { EmployerProfileComponent } from './profile/employer-profile/employer-profile.component';
import {TableModule} from "primeng/table";
import {DialogModule} from "primeng/dialog";
import {PasswordModule} from "primeng/password";
import { ChangePswComponent } from './profile/user-profile/change-psw/change-psw.component';





@NgModule({
  declarations: [
    DesktopComponent,
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
  ],
})
export class DesktopModule { }
