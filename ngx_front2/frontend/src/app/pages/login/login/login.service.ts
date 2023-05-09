import { Injectable } from '@angular/core';
import {AuthenticationAPI} from "../../../services/API/AuthenticationAPI";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {AuthToken} from "../../../types/authToken";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  api:AuthenticationAPI = new AuthenticationAPI();
  constructor(private http: HttpClient) { }

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

  public getPasswordRequirements(): Observable<PasswordStrangeRequirement> {
    return this.http.get<PasswordStrangeRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }
}
