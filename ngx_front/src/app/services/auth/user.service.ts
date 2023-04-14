import {Injectable} from '@angular/core';
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {EventBusService} from "../eventBus/event-bus.service";
import {AppEvents} from "../eventBus/EventData";
import {Tokens} from "./Tokens";

const storageKeyName:string ='token';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _email: string|null=null;
  private tokenExpire: Date|null=null
  private _authorities:Set<string>= new Set<string>();
  private _token: string|null=null;
  private _id:number|null=null;
  private eventBusSub?: Subscription;
  constructor(private http: HttpClient,private eventBus:EventBusService) {
    this.eventBusSub = this.eventBus.on(AppEvents.LOGOUT, () => {
      this.logout();
    });
  }
  private logout() {
    console.log('разлогинились')
    this._token=null;
    this._email=null;
    this.tokenExpire=null;
    sessionStorage.clear();
    window.location.reload();
  }
  public isTokenExpire():boolean{
    if(this._email===null){
      return true;
    }
    if(this.tokenExpire!=null){
      if(this.tokenExpire > new Date()){
        return false;
      }
    }
    return true;
  }

  public update(token: Tokens | null){
    sessionStorage.removeItem(storageKeyName);
    sessionStorage.setItem(storageKeyName, <string>token?.access_token);
    this.parseToken(token?.access_token)
    }


  init() {
    if(sessionStorage.getItem(storageKeyName)){
      this.parseToken(sessionStorage.getItem('token'));
    }
  }

  private parseToken(token: string | null | undefined){
    if(token===null||token===undefined){
      return;
    }
    this._token=token;
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    const payload = JSON.parse(jsonPayload);
    this._email=payload.sub;
    console.log(payload)
    this._authorities = new Set<string>(payload.authorities);
    this.tokenExpire = new Date(payload.exp*1000);
    this._id = payload.id;
  }

  get email(): string {
    if(this._email!=null){
      return this._email;
    }
    return '';
  }

  get authorities(): Set<string> {
    return this._authorities;
  }

  get token(): string | null {
    console.log(this._token);
    return this._token;
  }
}
