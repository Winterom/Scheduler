import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";
import {Observable} from "rxjs";
import {httpOptions} from "../../../services/API/RootHTTPApi";

@Injectable({
  providedIn: 'root'
})
export class ApprovedEmailService {

  constructor(private http: HttpClient, private api:HTTPUsersAPI) {
  }
  approvedEmail(token:string,email:string):Observable<any>{
    return this.http.put(this.api.approvedEmail,{
      token:token,
      email:email
      }
      ,httpOptions)
  }
}
