import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {HTTPUsersAPI} from "./API/HTTPUsersAPI";
import {Observable} from "rxjs";
import {AuthToken} from "../types/auth/AuthToken";

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _id: string|null=null;
  private _email: string|null=null;
  private _token:string|null=null;
  private _isAuth:boolean=false;
  private _authorities:Set<string>|null = null;
  private _tokenExpire:Date|null = null;


  constructor(private http: HttpClient, private api:HTTPUsersAPI) {
  }
  get id(): string | null {
    return this._id;
  }

  set id(value: string | null) {
    this._id = value;
  }


  get email(): string | null {
    return this._email;
  }

  set email(value: string | null) {
    this._email = value;
  }

  get token(): string | null {
    if(this._tokenExpire!==null){
      if(this._tokenExpire?.getTime()>Date.now()){
        return this._token;
      }else {
        this.clear();
      }
    }
    return null;
  }

 get isAuth():boolean{
    return this._isAuth;
 }
  set token(value: string|null) {
    console.log('value '+value)
    if(value===null){
      this.clear();
      return;
    }
    this._token = value;
    try {
      const base64Url = value.split('.')[1];
      const base64 = base64Url.replace('-', '+').replace('_', '/');
      const rawToken = JSON.parse(this.decode(base64));
      this.id = rawToken.id;
      this.email = rawToken.sub;
      this._authorities = new Set<string>(rawToken.authorities);
      this._tokenExpire = new Date(rawToken.exp*1000);
      this._isAuth = true;
      return;
    }catch (e){
      this._isAuth=false;
      return;
    }

  }

  private clear(){
    this._id = null;
    this._email = null;
    this._token = null;
  }
  private decode(rawToken:string):string{
    return atob(rawToken);
  }

  public refreshing(): Observable<AuthToken>{
    return this.http.get<AuthToken>(this.api.refreshing,{withCredentials: true });
  }
}
