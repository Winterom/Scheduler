import {Data} from "@angular/router";

export class UserProfile {
  id: string ='';
  status: string ='';
  email: string ='';
  isEmailVerified:boolean = false;
  phone:string  ='';
  isPhoneVerified:boolean = false;
  createdAt:Data = new Date();
  credentialExpiredTime:Data = new Date();
  updatedAt:Data = new Date();
  roles:Set<Role> = new Set<Role>();
}
export class Role{
  title:string = '';
  description:string = '';
}
