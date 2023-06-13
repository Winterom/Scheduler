import {EWebsocketEvents} from "./EWebsocketEvents";


export interface IWsMessage<R> {
  event: EWebsocketEvents;
  payload: IWsMessageBody<R>
}

export enum ResponseStatus{
  OK='OK',ERROR='ERROR'
}

export interface IWsMessageBody<R>{
  data: R;
  responseStatus:ResponseStatus;
  errorMessages:string[];
}
