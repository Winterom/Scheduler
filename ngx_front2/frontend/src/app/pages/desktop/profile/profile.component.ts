import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfileService} from "./profile.service";
import {UserProfile} from "../../../types/UserProfile";
import {RegistrationService} from "../../auth/registration/registration.service";
import {AuthMessage} from "../../../messages/AuthMessages";
import addSuccessMMessage = AuthMessage.addSuccessMMessage;
import {MessageService} from "primeng/api";
import addErrorMessage = AuthMessage.addErrorMessage;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [MessageService]
})
export class ProfileComponent implements OnInit{
  userProfile:UserProfile  = new UserProfile();
  userForm: FormGroup;
  emailControl:FormControl;
  phoneControl:FormControl;
  sendEmailVerifyTokenLoaded:boolean=false;
  sendPhoneVerifyTokenLoaded:boolean = false;
  userFormLoading: boolean = false;
  userFormChanged: boolean = false;

  constructor(private profileService:ProfileService,
              private registrationService:RegistrationService,
              private messageService: MessageService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.phoneControl = new FormControl<number>(0);
    this.userForm = new FormGroup<any>({
      emailControl: this.emailControl,
      phoneControl: this.phoneControl
    })
  }
  ngOnInit(): void {
    this.profileService.profile().subscribe({next:data=> {
        this.userProfile = data;
        this.emailControl.setValue(this.userProfile.email, {emitEvent: false});
        this.phoneControl.setValue(this.userProfile.phone, {emitEvent: false});
      }})
    this.userForm.valueChanges.subscribe(()=>
      this.userFormChanged=true
    )
  }
  submitUserFrm() {

  }

  checkPhone() {
    if (this.phoneControl.errors != null||this.phoneControl.value===0) {
      return;
    }
    let rawPhone: string = this.phoneControl.value.toString();
    const phone = rawPhone.replaceAll('-','')
      .replace('(','')
      .replace(')','')
      .replace(' ','');
    if(phone===this.userProfile.phone){
      return;
    }
    this.registrationService.checkPhone(phone).subscribe({
      next: () => {
        addSuccessMMessage(this.messageService,'Телефон введен правильно и свободен',null)
        this.phoneControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.phoneControl.setErrors({phoneBusy:true});
      }
    })
  }

  checkEmail() {
    if (this.emailControl.errors != null) {
      return;
    }
    const email:string = this.emailControl.value;
    if(email===this.userProfile.email){
      return;
    }
    this.registrationService.checkEmail(email).subscribe({
      next: () => {
        addSuccessMMessage(this.messageService,'email введен правильно и свободен',null);
        this.emailControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.emailControl.setErrors({emailBusy:true});
      }
    })
  }


  sendEmailVerifyToken() {

  }

  sendPhoneVerifyToken() {

  }
}
