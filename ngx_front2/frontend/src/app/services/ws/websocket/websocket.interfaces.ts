import {Observable} from "rxjs";

export interface IWebsocketService {
  on<T>(event: string): Observable<T>;
  send(event: string, data: any): void;
  status: Observable<boolean>;
}


export interface IWsMessage<T> {
  event: string;
  data: T;
}
