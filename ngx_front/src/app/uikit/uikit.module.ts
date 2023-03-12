import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HamburgerComponent } from './hamburger/hamburger.component';
import {FormsModule} from "@angular/forms";
import { TableComponent } from './table/table.component';
import { CheckboxComponent } from './checkbox/checkbox.component';



@NgModule({
  declarations: [
    HamburgerComponent,
    TableComponent,
    CheckboxComponent
  ],
  exports: [
    HamburgerComponent,
    TableComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class UikitModule { }
