import {Injectable} from '@angular/core';
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";
import {HttpClient} from "@angular/common/http";
import {Observable, shareReplay} from "rxjs";
import {PasswordStrengthRequirement} from "../../../types/auth/PasswordStrengthRequirement";
import {httpOptions} from "../../../services/API/RootHTTPApi";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient,private api:HTTPUsersAPI) { }

  public getPasswordRequirements(): Observable<PasswordStrengthRequirement> {
    return this.http.get<PasswordStrengthRequirement>(
      this.api.passwordRequirements,
      httpOptions
    );
  }
  public registration(email:string,
                      phone:string,
                      surname:string,
                      name:string,
                      lastname:string,
                      password:string):Observable<any>{
    return this.http.post(this.api.registration,{
      email: email,
      phone:phone,
      surname:surname,
      name:name,
      lastname:lastname,
      password:password
    },httpOptions).pipe(shareReplay());
  }

  public checkEmail(email:string):Observable<any>{
    return this.http.get(this.api.checkEmail+email,httpOptions)
  }
  public checkPhone(phone:string):Observable<any>{
    return this.http.get(this.api.checkPhone+phone,httpOptions)
  }
}
