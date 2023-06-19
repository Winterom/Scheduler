import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";


@Component({
  selector: 'app-desktop',
  templateUrl: './desktop.component.html',
  styleUrls: ['./desktop.component.scss']
})
export class DesktopComponent implements OnInit{

  constructor(private router: Router) {
  }
  ngOnInit(): void {}

  goSettings() {
    this.router.navigate(['settings'])
  }

  goProfile() {
    this.router.navigate(['profile'])
  }

  goRolesAndAuthorities() {
    this.router.navigate(['roles-and-authorities'])
  }

  goUserList() {
    this.router.navigate(['users'])
  }
}
