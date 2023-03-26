import {Component} from '@angular/core';
import {UserService} from "../../../../services/auth/user.service";

@Component({
  selector: 'app-left-sidebar',
  templateUrl: './menu-s-b.component.html',
  styleUrls: ['./menu-s-b.component.scss']
})
export class MenuSBComponent {

  constructor(public userService:UserService) { }

}
