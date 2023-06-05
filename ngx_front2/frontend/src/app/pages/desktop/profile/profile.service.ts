import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HTTPUsersAPI} from "../../../services/API/HTTPUsersAPI";
import {Observable} from "rxjs";
import {httpOptions} from "../../../services/API/RootHTTPApi";
import {UserProfile} from "../../../types/UserProfile";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient, private api:HTTPUsersAPI) { }
  public profile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(
      this.api.getProfile,
      httpOptions
    )
  }
}
