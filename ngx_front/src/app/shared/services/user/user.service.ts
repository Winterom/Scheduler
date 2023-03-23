import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {AuthenticationAPI} from "../API/AuthenticationAPI";
import {User} from "./User";
import {EventBusService} from "../eventBus/event-bus.service";
import {AppEvents} from "../eventBus/EventData";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _user:User|null = null;
  eventBusSub?: Subscription;

  private api:AuthenticationAPI = new AuthenticationAPI()
  constructor(private http: HttpClient,private eventBus:EventBusService) {
    this.eventBusSub = this.eventBus.on(AppEvents.LOGOUT, () => {
      this.logout();
    });
  }

   private login(token: string, password: string): Observable<User> {
    return this.http.post<User>(
      this.api.auth_api,
      {
        token,
        password,
      },
      httpOptions
    )
  }

  private refresh():Observable<User>{
    return this.http.get<User>(this.api.getRefresh);
  }

  public init(){
      const token = sessionStorage.getItem('token');

  }

  get user(): User | null {
    return this._user;
  }

  set user(value: User | null) {
    this._user = value;
  }

  public isLogin():boolean{
    return this._user?.token != null;
  }
  private logout() {
    this._user=null;
    sessionStorage.clear();
    window.location.reload();
  }
}


