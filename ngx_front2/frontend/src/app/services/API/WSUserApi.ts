import {WSApi} from "./WSApi";

export class WSUserApi extends WSApi{
  private readonly _user_api:string = 'users';
  private readonly _getProfile:string = 'profile'
  private readonly _getRoles:string ='roles'

  get getProfile(): string {
    return super.getRoot()+'/'+this._user_api+'/'+this._getProfile;
  }

  get getRoles(): string {
    return super.getRoot()+'/'+this._getRoles;
  }
}
