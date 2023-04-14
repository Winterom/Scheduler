import {Component} from '@angular/core';
import {UserService} from "./services/auth/user.service";

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
