import {WSResponseEvents} from "../../../types/WSResponseEvents";


export interface IWsMessage<R> {
  event: WSResponseEvents;
  payload: IWsMessageBody<R>
}

export enum ResponseStatus{
  OK,ERROR
}

export interface IWsMessageBody<R>{
  data: R;
  responseStatus:ResponseStatus;
  errorMessages:string[];
}
