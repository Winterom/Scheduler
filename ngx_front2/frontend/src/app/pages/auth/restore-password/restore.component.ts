import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-reset',
  templateUrl: './restore.component.html',
  styleUrls: ['../login.scss']
})
export class RestoreComponent {
  loading: boolean = false;

  constructor(private router: Router) {
  }
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {

  }
}
