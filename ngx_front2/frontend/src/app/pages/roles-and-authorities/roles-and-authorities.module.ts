import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RolesAndAuthoritiesComponent} from "./roles-and-authorities.component";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {TreeModule} from "primeng/tree";
import { RolesComponent } from './roles/roles.component';
import { CurrentRoleComponent } from './current-role/current-role.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {ButtonModule} from "primeng/button";
import {TooltipModule} from "primeng/tooltip";
import {DropdownModule} from "primeng/dropdown";
import {TableModule} from "primeng/table";
import { AuthoritiesTableComponent } from './current-role/authorities-table/authorities-table.component';
import {TreeTableModule} from "primeng/treetable";
import {ToastModule} from "primeng/toast";

const routes: Routes = [
  {
    path: '',
    component: RolesAndAuthoritiesComponent
  }
];

@NgModule({
  declarations: [
    RolesAndAuthoritiesComponent,
    RolesComponent,
    CurrentRoleComponent,
    AuthoritiesTableComponent
  ],
  exports:[RouterModule],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(routes),
        TreeModule,
        FormsModule,
        InputTextModule,
        ReactiveFormsModule,
        InputTextareaModule,
        ButtonModule,
        TooltipModule,
        DropdownModule,
        TableModule,
        TreeTableModule,
        ToastModule
    ]
})
export class RolesAndAuthoritiesModule { }
