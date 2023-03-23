import { Component } from '@angular/core';
import {UserService} from "./shared/services/user/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private user:UserService) {
      this.user.init();
  }
}
