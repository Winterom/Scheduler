import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfileService} from "./profile.service";
import {UserProfile} from "../../../types/UserProfile";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit{
  userProfile:UserProfile  = new UserProfile();
  userForm: FormGroup;
  emailControl:FormControl;
  phoneControl:FormControl;

  constructor(private profileService:ProfileService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    })
  }
  ngOnInit(): void {
    this.profileService.profile().subscribe({next:data=>{
        this.userProfile = data;
        this.userForm.setValue({
          emailControl: this.userProfile.email,
          phoneControl: this.userProfile.phone
        })
      }})
  }
  submitUserFrm() {

  }

  checkPhone() {
    const rawPhone:string = this.phoneControl.value;
    const phone = rawPhone.replaceAll('-','')
      .replace('(','')
      .replace(')','')
      .replace(' ','');
  }

  checkEmail() {

  }


}
