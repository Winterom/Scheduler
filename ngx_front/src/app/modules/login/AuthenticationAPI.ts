import {RootAPI} from "../../shared/services/API/RootAPI";

export class AuthenticationAPI extends RootAPI{
    private _auth_api:string = "auth";

  get auth_api(): string {
    return super.getRoot()+"/"+this._auth_api;
  }
}
