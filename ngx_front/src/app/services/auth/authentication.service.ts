import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationAPI} from "../API/AuthenticationAPI";
import {Tokens} from "./Tokens";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private api:AuthenticationAPI = new AuthenticationAPI()
  constructor(private http: HttpClient) {
  }

   public login(emailOrPhone: string, password: string): Observable<Tokens> {
    return this.http.post<Tokens>(
      this.api.auth_api,
      {
        emailOrPhone,
        password,
      },
      httpOptions
    )
  }





}


