import { Injectable } from '@angular/core';
import {AuthenticationAPI} from "../API/AuthenticationAPI";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PasswordStrangeRequirement} from "../../modules/login/reset-password/PasswordStrangeRequirement";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })}
@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {
  private api:AuthenticationAPI = new AuthenticationAPI();

  constructor(private http: HttpClient) {
  }

  public getPasswordRequirements(): Observable<PasswordStrangeRequirement> {
    return this.http.get<PasswordStrangeRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }
}
