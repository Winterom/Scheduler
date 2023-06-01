import {NgModule} from '@angular/core';
import {RouterModule, Routes, TitleStrategy} from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {RestoreComponent} from "./pages/auth/restore-password/restore.component";
import {DesktopComponent} from "./pages/desktop/desktop.component";
import {RegistrationComponent} from "./pages/auth/registration/registration.component";
import {ChangePasswordComponent} from "./pages/auth/change-password/change-password.component";
import {TitleStrategyService} from "./services/title-strategy.service";
import {desktopGuard} from "./guards/desktop.guard";
import {ApprovedEmailComponent} from "./pages/auth/approved-email/approved-email.component";
import {GlobalSettingsComponent} from "./pages/global-settings/global-settings.component";
import {ProfileComponent} from "./pages/desktop/profile/profile.component";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'password/reset',component:RestoreComponent},
  {path:'password/restore',component:ChangePasswordComponent},
  {path:'approved',component:ApprovedEmailComponent},
  {path:'profile',component:ProfileComponent},
  {path:'desktop',component:DesktopComponent,canActivate:[desktopGuard]},
  {path:'settings',component:GlobalSettingsComponent,
    loadChildren:()=>import('./pages/global-settings/settings.module').then(m=>m.SettingsModule)},
  {path:'**',redirectTo:'login', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers:[{provide: TitleStrategy, useClass: TitleStrategyService}]
})
export class AppRoutingModule { }
