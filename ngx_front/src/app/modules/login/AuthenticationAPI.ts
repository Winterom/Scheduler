import {RootAPI} from "../../shared/services/API/RootAPI";

export class AuthenticationAPI extends RootAPI{
    private _auth_api:string = "auth";
    private _sendCodeForResetPassword ='';

  get sendCodeForResetPassword(): string {
    return super.getRoot()+"/"+this._sendCodeForResetPassword;
  }

  get auth_api(): string {
    return super.getRoot()+"/"+this._auth_api;
  }
}
