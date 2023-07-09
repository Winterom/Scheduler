import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DesktopComponent} from './desktop.component';
import {PanelModule} from "primeng/panel";
import {AvatarModule} from "primeng/avatar";


@NgModule({
  declarations: [
    DesktopComponent,
  ],
    imports: [
        CommonModule,
        PanelModule,
        AvatarModule,
    ],
})
export class DesktopModule { }
