import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './user-list/user-list.component';
import { UserByIdComponent } from './user-list/user-by-id/user-by-id.component';
import { GlobalSettingsComponent } from './global-settings/global-settings.component';
import { AuthorityComponent } from './authrority/authority.component';
import {RouterModule, Routes} from "@angular/router";
import {UikitModule} from "../../uikit/uikit.module";
import { AppSettingsComponent } from './global-settings/app-settings/app-settings.component';
import { MailSettingsComponent } from './global-settings/mail-settings/mail-settings.component';
import { SecuritySettingsComponent } from './global-settings/security-settings/security-settings.component';
import {StompModule} from "../../services/WS/stomp.module";

const routes:Routes=[
  {path:'',component:GlobalSettingsComponent},
  {path:'settings',component:GlobalSettingsComponent},
  {path:'users',component:UserListComponent},
  {path:'users/:id',component:UserByIdComponent},
  {path:'authority',component:AuthorityComponent},
]

@NgModule({
  declarations: [
    UserListComponent,
    UserByIdComponent,
    GlobalSettingsComponent,
    AuthorityComponent,
    AppSettingsComponent,
    MailSettingsComponent,
    SecuritySettingsComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        UikitModule,
        StompModule
    ]
})
export class AdminModule { }
