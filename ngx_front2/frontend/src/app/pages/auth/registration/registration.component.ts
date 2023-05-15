import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RegistrationService} from "./registration.service";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login.scss']
})
export class RegistrationComponent implements OnInit{
  loading: boolean = false;
  pswStrangeReq:PasswordStrangeRequirement = new PasswordStrangeRequirement;
  constructor(private router: Router,private regService:RegistrationService) {}
  ngOnInit(): void {
    this.regService.getPasswordRequirements().
      subscribe({next:data=>{
      this.pswStrangeReq=data;
      }})
  }
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {

  }


}
