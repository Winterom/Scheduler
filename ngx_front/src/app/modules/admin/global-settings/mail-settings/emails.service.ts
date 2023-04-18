import { Injectable } from '@angular/core';
import {EmailsWSApi} from "./EmailsWSApi";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {SORT_DIRECTION} from "../../../../uikit/table/TableDefinition";
import {RowEmailTable} from "./AllEmailsTable";

export interface EmailsSortParam{
    header:string;
    direction:SORT_DIRECTION;
}


@Injectable({
  providedIn: 'root'
})
export class EmailsService {
  private api:EmailsWSApi = new EmailsWSApi();
  constructor(private http: HttpClient) { }
  public emailList(param:EmailsSortParam[]): Observable<RowEmailTable[]> {
    let httpParam = new HttpParams();
    param.forEach(function (value){
        httpParam = httpParam.append('sort',value.header+','+value.direction)
    })
    return this.http.get<RowEmailTable[]>(
      this.api.getEmailsListApi(),
      {
        params:httpParam
      },
    )
  }
}
