import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WidgetLayoutComponent} from "./widget-layout.component";
import {RouterModule, Routes} from "@angular/router";
import {GlobalSettingsComponent} from "../global-settings/global-settings.component";
import {SettingsModule} from "../global-settings/settings.module";
import {SidebarModule} from "primeng/sidebar";
import {ButtonModule} from "primeng/button";

const routes: Routes =[
  {path:'settings',component:GlobalSettingsComponent}
]

@NgModule({
  declarations: [
    WidgetLayoutComponent
  ],
  imports: [
    CommonModule,
    SettingsModule,
    RouterModule.forChild(routes),
    SidebarModule,
    ButtonModule,
  ]
})
export class WidgetModule { }
