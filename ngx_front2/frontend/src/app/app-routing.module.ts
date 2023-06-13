import {NgModule} from '@angular/core';
import {RouterModule, Routes, TitleStrategy} from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {RestoreComponent} from "./pages/auth/restore-password/restore.component";
import {DesktopComponent} from "./pages/desktop/desktop.component";
import {RegistrationComponent} from "./pages/auth/registration/registration.component";
import {ChangePasswordComponent} from "./pages/auth/change-password/change-password.component";
import {TitleStrategyService} from "./services/title-strategy.service";
import {isAuthentication} from "./guards/isAuthentication";
import {ApprovedEmailComponent} from "./pages/auth/approved-email/approved-email.component";
import {GlobalSettingsComponent} from "./pages/global-settings/global-settings.component";
import {ProfileComponent} from "./pages/profile/profile.component";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'password/reset',component:RestoreComponent},
  {path:'password/restore',component:ChangePasswordComponent},
  {path:'approved',component:ApprovedEmailComponent},
  {path:'profile',component:ProfileComponent,loadChildren:()=>
      import('./pages/profile/profile.module').then(m=>m.ProfileModule),
      canActivate:[isAuthentication]},
  {path:'desktop',component:DesktopComponent,canActivate:[isAuthentication]},
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
