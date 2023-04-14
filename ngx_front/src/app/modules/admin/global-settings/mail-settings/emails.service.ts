import { Injectable } from '@angular/core';
import {EmailsWSApi} from "./EmailsWSApi";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AllEmailsTable} from "./AllEmailsTable";


@Injectable({
  providedIn: 'root'
})
export class EmailsService {
  private api:EmailsWSApi = new EmailsWSApi();
  constructor(private http: HttpClient) { }
  public emailList(): Observable<AllEmailsTable> {
    return this.http.get<AllEmailsTable>(
      this.api.getEmailsListApi(),
      {

      },
    )
  }
}
