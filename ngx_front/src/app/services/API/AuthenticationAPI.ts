import {RootHTTPApi} from "./RootHTTPApi";

export class AuthenticationAPI extends RootHTTPApi{

    private _auth_api:string = "auth";
    private _sendCodeForResetPassword ='';
    private _passwordRequirements = 'properties/security/password/strange';

  get sendCodeForResetPassword(): string {
    return this.auth_api+"/"+this._sendCodeForResetPassword;
  }

  get auth_api(): string {
    return super.getRoot()+"/"+this._auth_api;
  }

  get passwordRequirements(): string {
    return super.getRoot()+"/"+ this._passwordRequirements;
  }

}
