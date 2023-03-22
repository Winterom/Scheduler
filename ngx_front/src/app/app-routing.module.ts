import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./modules/login/login/login.component";
import {DashboardLayoutComponent} from "./modules/dashboard/dashboard-layout/dashboard-layout.component";
import {ResetPasswordComponent} from "./modules/login/reset-password/reset-password.component";


const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"reset",component:ResetPasswordComponent},
  {path:"board",component:DashboardLayoutComponent,loadChildren:()=>import('./modules/dashboard/dashboard.module').then(m=>m.DashboardModule)},
  {path:"**",redirectTo:"board"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
