import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login/login.component";
import {ResetComponent} from "./pages/login/reset/reset.component";
import {DesktopComponent} from "./pages/desktop/desktop.component";
import {WidgetLayoutComponent} from "./pages/widget-layout/widget-layout.component";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'reset',component:ResetComponent},
  {path:'desktop',component:DesktopComponent
  ,loadChildren:()=>import('./pages/desktop/desktop.module').then(m=>m.DesktopModule)
    /*,canActivate:[() => inject(desktopGuard).canActivate()*/},
  {path:'widget',component:WidgetLayoutComponent,
  loadChildren:()=>import('./pages/widget-layout/widget.module').then(t=>t.WidgetModule)},
  {path:"**",redirectTo:"login"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers:[]
})
export class AppRoutingModule { }
