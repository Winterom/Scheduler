import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './user-list/user-list.component';
import { UserByIdComponent } from './user-list/user-by-id/user-by-id.component';
import { GlobalSettingsComponent } from './global-settings/global-settings.component';
import { AuthorityComponent } from './authrority/authority.component';
import {RouterModule, Routes} from "@angular/router";
import {UikitModule} from "../../uikit/uikit.module";

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
    AuthorityComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        UikitModule
    ]
})
export class AdminModule { }
