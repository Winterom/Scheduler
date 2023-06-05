import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";
import {Observable} from "rxjs";
import {httpOptions} from "../../../services/API/RootHTTPApi";

@Injectable({
  providedIn: 'root'
})
export class RestoreService {

  constructor(private http: HttpClient,private api:HTTPUsersAPI) {
  }
  sendAccountEmail(email:string):Observable<any>{
    return this.http.get(this.api.sendCodeForResetPassword+email,httpOptions);
  }
}
