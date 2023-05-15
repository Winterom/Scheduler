import { Injectable } from '@angular/core';
import {UsersAPI} from "../../../services/API/UsersAPI";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {httpOptions} from "../../../services/API/RootHTTPApi";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient,private api:UsersAPI) { }

  public getPasswordRequirements(): Observable<PasswordStrangeRequirement> {
    return this.http.get<PasswordStrangeRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }
}
