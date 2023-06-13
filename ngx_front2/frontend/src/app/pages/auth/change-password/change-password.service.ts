import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PasswordStrengthRequirement} from "../../../types/auth/PasswordStrengthRequirement";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  constructor(private http: HttpClient, private api:HTTPUsersAPI) { }

  public getPasswordRequirements(): Observable<PasswordStrengthRequirement> {
    return this.http.get<PasswordStrengthRequirement>(
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
