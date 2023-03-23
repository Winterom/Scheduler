import {RootApi} from "./RootApi";

export class AuthenticationAPI extends RootApi{

    private _auth_api:string = "auth";
    private _sendCodeForResetPassword ='';
    private _passwordRequirements = '';
    private _getRefresh = '';

  get sendCodeForResetPassword(): string {
    return this.auth_api+"/"+this._sendCodeForResetPassword;
  }

  get auth_api(): string {
    return super.getRoot()+"/"+this._auth_api;
  }

  get passwordRequirements(): string {
    return  this.auth_api+"/"+this._passwordRequirements;
  }
  get getRefresh(): string {
    return this.auth_api+"/"+this._getRefresh;
  }
}
