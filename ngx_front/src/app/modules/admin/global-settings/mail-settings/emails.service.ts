import { Injectable } from '@angular/core';
import {EmailsWSApi} from "./EmailsWSApi";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AllEmailsTable} from "./AllEmailsTable";
import {SORT_DIRECTION} from "../../../../uikit/table/TableDefinition";

export interface EmailsRequestParam{
    header:string;
    direction:SORT_DIRECTION;
}

@Injectable({
  providedIn: 'root'
})
export class EmailsService {
  private api:EmailsWSApi = new EmailsWSApi();
  constructor(private http: HttpClient) { }
  public emailList(param:EmailsRequestParam[]): Observable<AllEmailsTable> {
    const httpParam = new HttpParams();
    param.forEach(function (value){

    })
    return this.http.get<AllEmailsTable>(
      this.api.getEmailsListApi(),
      {

      },
    )
  }
}
