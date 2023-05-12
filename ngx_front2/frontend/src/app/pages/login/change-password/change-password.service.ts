import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {AuthenticationAPI} from "../../../services/API/AuthenticationAPI";

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  constructor(private http: HttpClient, private api:AuthenticationAPI) { }

  public getPasswordRequirements(): Observable<PasswordStrangeRequirement> {
    return this.http.get<PasswordStrangeRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }
}
