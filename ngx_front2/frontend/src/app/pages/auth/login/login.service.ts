import {Injectable} from '@angular/core';
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {AuthToken} from "../../../types/auth/AuthToken";


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private api:HTTPUsersAPI) { }

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
