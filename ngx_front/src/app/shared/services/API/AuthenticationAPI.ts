import {RootApi} from "./RootApi";

export class AuthenticationAPI extends RootApi{
    private _auth_api:string = "auth";
    private _sendCodeForResetPassword ='';
    private _passwordRequirements = '';

  get sendCodeForResetPassword(): string {
    return super.getRoot()+"/"+this._sendCodeForResetPassword;
  }

  get auth_api(): string {
    return super.getRoot()+"/"+this._auth_api;
  }

  get passwordRequirements(): string {
    return super.getRoot()+"/"+this._passwordRequirements;
  }
}
