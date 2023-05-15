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
    },httpOptions);
  }

  public checkEmail(email:string):Observable<any>{
    return this.http.post(this.api.checkEmail,{
      email:email
    },httpOptions)
  }
}
