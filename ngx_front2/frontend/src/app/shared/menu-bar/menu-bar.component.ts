import {Component, Input} from '@angular/core';
import {MenuItem} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.scss']
})
export class MenuBarComponent {
  @Input()items: MenuItem[]=[];
  constructor(private router: Router) {
  }

  closeApp() {
    this.router.navigate(['desktop']);
  }
}
