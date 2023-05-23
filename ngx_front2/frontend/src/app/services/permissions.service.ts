import { Injectable } from '@angular/core';
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor(private user:UserService) { }

  canActivateDesktop():boolean{
    return this.user.isAuth;
  }

}
