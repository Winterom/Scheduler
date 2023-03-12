import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DashboardLayoutComponent} from "./dashboard-layout/dashboard-layout.component";
import {RouterModule, RouterOutlet, Routes} from "@angular/router";
import {UikitModule} from "../../uikit/uikit.module";
import {AdminModule} from "../admin/admin.module";
import {DesktopComponent} from "../desktop/desktop/desktop.component";
import {DesktopModule} from "../desktop/desktop.module";
import { TopMenuSBComponent } from './dashboard-layout/left-sidebar/top-sb-menu/top-menu-s-b.component';
import { BottomMenuSBComponent } from './dashboard-layout/left-sidebar/bottom-sb-menu/bottom-menu-s-b.component';
import {TopMenuComponent} from "./dashboard-layout/top-menu/top-menu.component";
import {MenuSBComponent} from "./dashboard-layout/left-sidebar/menu-s-b.component";
import {ProfileComponent} from "../profile/profile.component";
import { ChatNotificationComponent } from './dashboard-layout/top-menu/chat-notification/chat-notification.component';
import { NotificationComponent } from './dashboard-layout/top-menu/notification/notification.component';



const routes: Routes =[
  {path:'admin',loadChildren:()=>import('../admin/admin.module').then(m=>m.AdminModule)},
  {path:'desktop',component:DesktopComponent},
  {path:'profile',component:ProfileComponent},
  {path:'**',redirectTo:'desktop'}
];

@NgModule({
  declarations: [
    TopMenuComponent,
    MenuSBComponent,
    DashboardLayoutComponent,
    TopMenuSBComponent,
    BottomMenuSBComponent,
    ChatNotificationComponent,
    NotificationComponent,
  ],
  exports: [
    TopMenuComponent
  ],
    imports: [
        CommonModule,
        RouterOutlet,
        UikitModule,
        AdminModule,
        DesktopModule,
        RouterModule.forChild(routes),
    ]
})
export class DashboardModule { }
