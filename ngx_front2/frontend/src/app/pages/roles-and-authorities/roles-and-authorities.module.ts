import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RolesAndAuthoritiesComponent} from "./roles-and-authorities.component";
import {SharedModule} from "../../shared/shared.module";



@NgModule({
  declarations: [
    RolesAndAuthoritiesComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class RolesAndAuthoritiesModule { }
