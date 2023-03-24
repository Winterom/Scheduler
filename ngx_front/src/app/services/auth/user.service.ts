import { Injectable } from '@angular/core';
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {EventBusService} from "../eventBus/event-bus.service";
import {AppEvents} from "../eventBus/EventData";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _token: string|null=null;
  eventBusSub?: Subscription;
  constructor(private http: HttpClient,private eventBus:EventBusService) {
    this.eventBusSub = this.eventBus.on(AppEvents.LOGOUT, () => {
      this.logout();
    });
  }
  private logout() {
    this._token=null;
    sessionStorage.clear();
    window.location.reload();
  }
  public isLogin():boolean{
    return true;
  }

  public update(token: string | null){
      this.parseToken(token)
  }

  init() {
    if(sessionStorage.getItem('token')){
      this.parseToken(sessionStorage.getItem('token'));
    }
  }

  private parseToken(token:string|null){
    if(token===null){
      return;
    }
    this._token=token;
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    console.log(jsonPayload);
  }
}
