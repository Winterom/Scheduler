import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HamburgerComponent } from './hamburger/hamburger.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { TableComponent } from './table/table.component';
import { CheckboxComponent } from './checkbox/checkbox.component';
import { InputComponent } from './input/input.component';
import { ButtonComponent } from './button/button.component';




@NgModule({
  declarations: [
    HamburgerComponent,
    TableComponent,
    CheckboxComponent,
    InputComponent,
    ButtonComponent
  ],
    exports: [
        HamburgerComponent,
        TableComponent,
        InputComponent,
        ButtonComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class UikitModule { }
