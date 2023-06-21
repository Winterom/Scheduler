import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RolesAndAuthoritiesComponent} from "./roles-and-authorities.component";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {TreeModule} from "primeng/tree";
import { RolesComponent } from './roles/roles.component';

const routes: Routes = [
  {
    path: '',
    component: RolesAndAuthoritiesComponent
  }
];

@NgModule({
  declarations: [
    RolesAndAuthoritiesComponent,
    RolesComponent
  ],
  exports:[RouterModule],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(routes),
        TreeModule
    ]
})
export class RolesAndAuthoritiesModule { }
