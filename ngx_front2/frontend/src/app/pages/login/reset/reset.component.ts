import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['../../login/login.scss']
})
export class ResetComponent {
  loading: boolean = false;

  constructor(private router: Router) {
  }
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {

  }
}
