import { Injectable } from '@angular/core';
import {AuthenticationAPI} from "../../../services/API/AuthenticationAPI";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  api:AuthenticationAPI = new AuthenticationAPI();
  constructor(private http: HttpClient) { }
}
