import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UsersAPI} from "../../../services/API/UsersAPI";

@Injectable({
  providedIn: 'root'
})
export class RestoreService {

  constructor(private http: HttpClient,private api:UsersAPI) { }
}
