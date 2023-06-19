import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RolesAndAuthoritiesComponent} from "./roles-and-authorities.component";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: RolesAndAuthoritiesComponent
  }
];

@NgModule({
  declarations: [
    RolesAndAuthoritiesComponent
  ],
  exports:[RouterModule],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class RolesAndAuthoritiesModule { }
