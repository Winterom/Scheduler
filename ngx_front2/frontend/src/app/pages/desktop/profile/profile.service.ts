import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UsersAPI} from "../../../services/API/UsersAPI";
import {Observable} from "rxjs";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {UserProfile} from "../../../types/UserProfile";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient, private api:UsersAPI) { }
  public profile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(
      this.api.getProfile,
      httpOptions
    )
  }
}
