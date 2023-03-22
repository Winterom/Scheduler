import {User} from "./User";

export class UserImpl implements User{
  private _token:string|null = null;
  private _refresh:string|null = null;

  constructor(token: string | null, refresh: string | null) {
    this._token = token;
    this._refresh = refresh;
  }

  get token(): string | null {
    return this._token;
  }

  set token(value: string | null) {
    this._token = value;
  }

  get refresh(): string | null {
    return this._refresh;
  }

  set refresh(value: string | null) {
    this._refresh = value;
  }
}
