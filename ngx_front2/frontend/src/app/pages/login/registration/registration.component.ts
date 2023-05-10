import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../../login/login.scss']
})
export class RegistrationComponent {
  loading: boolean = false;
  pswStrange:string='';
  constructor(private router: Router) {}
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {

  }
}
