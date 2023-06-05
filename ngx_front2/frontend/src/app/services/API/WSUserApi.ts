import {WSApi} from "./WSApi";

export class WSUserApi extends WSApi{
  private readonly _user_api:string = 'users';
  private readonly _getProfile:string = 'profile'

  get getProfile(): string {
    return super.getRoot()+'/'+this._user_api+'/'+this._getProfile;
  }
}
