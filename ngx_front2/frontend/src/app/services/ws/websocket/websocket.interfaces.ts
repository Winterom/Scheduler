



export interface IWsMessage<R> {
  event: string;
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
