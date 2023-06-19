import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalSettingsComponent} from "./global-settings.component";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: GlobalSettingsComponent
  }
];
@NgModule({
  declarations: [
  GlobalSettingsComponent
  ],
  exports:[RouterModule],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    SharedModule,
  ]
})
export class SettingsModule { }
