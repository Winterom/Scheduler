import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UsersListComponent} from "./users-list.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: UsersListComponent
  }
];

@NgModule({
  declarations: [
    UsersListComponent
  ],
  exports:[RouterModule],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ]
})
export class UsersListModule { }
