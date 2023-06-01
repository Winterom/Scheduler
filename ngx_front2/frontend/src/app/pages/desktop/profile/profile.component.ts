import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  userForm: FormGroup;
  emailControl:FormControl;
  phoneControl:FormControl;

  constructor() {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    })
  }
  submitUserFrm() {

  }

  checkPhone() {
    const rawPhone:string = this.phoneControl.value;
    const phone = rawPhone.replaceAll('-','')
      .replace('(','')
      .replace(')','')
      .replace(' ','');
    console.log(phone)
  }

  checkEmail() {

  }
}
