
export interface IWsMessage<R> {
  event: string;
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
