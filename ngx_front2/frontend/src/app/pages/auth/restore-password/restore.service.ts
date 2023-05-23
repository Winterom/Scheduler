import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UsersAPI} from "../../../services/API/UsersAPI";
import {Observable} from "rxjs";
import {httpOptions} from "../../../services/API/RootHTTPApi";

@Injectable({
  providedIn: 'root'
})
export class RestoreService {

  constructor(private http: HttpClient,private api:UsersAPI) {
  }
  sendAccountEmail(email:string):Observable<any>{
    return this.http.get(this.api.sendCodeForResetPassword+email,httpOptions);
  }
}
