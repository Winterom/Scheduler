import { Injectable } from '@angular/core';
import {UsersAPI} from "../../../services/API/UsersAPI";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {AuthToken} from "../../../types/authToken";


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private api:UsersAPI) { }

  public login(emailOrPhone: string, password: string): Observable<AuthToken> {
    return this.http.post<AuthToken>(
      this.api.auth_api,
      {
        emailOrPhone,
        password,
      },
      httpOptions
    )
  }

}
