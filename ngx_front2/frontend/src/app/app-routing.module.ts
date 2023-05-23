import {NgModule} from '@angular/core';
import {RouterModule, Routes, TitleStrategy} from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {RestoreComponent} from "./pages/auth/restore-password/restore.component";
import {DesktopComponent} from "./pages/desktop/desktop.component";
import {WidgetLayoutComponent} from "./pages/widget-layout/widget-layout.component";
import {RegistrationComponent} from "./pages/auth/registration/registration.component";
import {ChangePasswordComponent} from "./pages/auth/change-password/change-password.component";
import {TitleStrategyService} from "./services/title-strategy.service";
import {desktopGuard} from "./guards/desktop.guard";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'restore',component:RestoreComponent},
  {path:'change',component:ChangePasswordComponent},
  {path:'desktop',component:DesktopComponent
  ,loadChildren:()=>import('./pages/desktop/desktop.module').then(m=>m.DesktopModule)
  ,canActivate:[desktopGuard]},
  {path:'app',component:WidgetLayoutComponent,
  loadChildren:()=>import('./pages/widget-layout/widget.module').then(t=>t.WidgetModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers:[{provide: TitleStrategy, useClass: TitleStrategyService}]
})
export class AppRoutingModule { }
