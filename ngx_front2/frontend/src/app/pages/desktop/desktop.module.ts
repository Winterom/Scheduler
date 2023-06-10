import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DesktopComponent} from './desktop.component';
import {PanelModule} from "primeng/panel";






@NgModule({
  declarations: [
    DesktopComponent,
  ],
  imports: [
    CommonModule,
    PanelModule,
  ],
})
export class DesktopModule { }
