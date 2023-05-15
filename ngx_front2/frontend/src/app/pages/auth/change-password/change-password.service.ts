import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {UsersAPI} from "../../../services/API/UsersAPI";

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  constructor(private http: HttpClient, private api:UsersAPI) { }

  public getPasswordRequirements(): Observable<PasswordStrangeRequirement> {
    return this.http.get<PasswordStrangeRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }

  public putUpdatePassword(email:string,token:string,password:string):Observable<any>{
    return this.http.put(this.api.updatePassword,{
      email: email,
      password:password,
      token: token
    },
      httpOptions)
  }
}
