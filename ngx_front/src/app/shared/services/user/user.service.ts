import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationAPI} from "../API/AuthenticationAPI";
import {User} from "./User";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _user:User|null = null;

  private api:AuthenticationAPI = new AuthenticationAPI()
  constructor(private http: HttpClient) { }

   public login(token: string, password: string): Observable<User> {
    return this.http.post<User>(
      this.api.auth_api,
      {
        token,
        password,
      },
      httpOptions
    )
  }


  get user(): User | null {
    return this._user;
  }

  set user(value: User | null) {
    this._user = value;
  }
}


