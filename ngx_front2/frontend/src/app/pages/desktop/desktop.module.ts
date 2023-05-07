import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DesktopComponent} from './desktop.component';
import {DockModule} from "primeng/dock";



@NgModule({
  declarations: [
    DesktopComponent
  ],
  imports: [
    CommonModule,
    DockModule
  ]
})
export class DesktopModule { }
