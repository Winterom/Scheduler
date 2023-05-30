import {RootHTTPApi} from "./RootHTTPApi";
import {Injectable} from "@angular/core";
@Injectable({
  providedIn: 'root'
})
export class UsersAPI extends RootHTTPApi{

    private readonly _auth_api:string = 'auth';
    private readonly _user_api:string = 'users'
    private readonly _sendCodeForResetPassword:string ='password/change/';
    private readonly _passwordRequirements:string = 'properties/security/password/strange';
    private readonly _updatePassword: string  = 'password/change';
    private readonly _registration:string='registration';
    private readonly _checkEmail:string='email/check';
    private readonly _checkPhone:string='phone/check';
    private readonly _approvedEmail:string='email/approved';


  get approvedEmail(): string {
    return this.user_api+'/'+this._approvedEmail;
  }

  get sendCodeForResetPassword(): string {
    return this.user_api+'/'+this._sendCodeForResetPassword;
  }

  get checkPhone(): string {
    return this.user_api+'/'+this._checkPhone+'/';
  }

  get checkEmail(): string {
    return this.user_api+'/'+this._checkEmail+'/';
  }

  get registration(): string {
    return this.user_api+'/'+this._registration;
  }

  get auth_api(): string {
    return super.getRoot()+'/'+this._auth_api;
  }

  private get user_api(): string {
    return super.getRoot()+'/'+this._user_api;
  }


  get updatePassword(): string {
    return this.user_api+'/'+this._updatePassword;
  }

  get passwordRequirements(): string {
    return super.getRoot()+'/'+ this._passwordRequirements;
  }


}
