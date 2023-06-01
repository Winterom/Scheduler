import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DesktopComponent} from './desktop.component';
import {PanelModule} from "primeng/panel";
import {ProfileComponent} from "./profile/profile.component";
import {SharedModule} from "../../shared/shared.module";
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {InputMaskModule} from "primeng/inputmask";




@NgModule({
  declarations: [
    DesktopComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    PanelModule,
    SharedModule,
    InputTextModule,
    ReactiveFormsModule,
    InputMaskModule
  ]
})
export class DesktopModule { }
