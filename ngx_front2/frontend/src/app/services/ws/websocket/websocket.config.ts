import {WebSocketSubjectConfig} from "rxjs/internal/observable/dom/WebSocketSubject";

import {IWsMessage} from "./websocket.interfaces";

export class WebsocketConfig implements WebSocketSubjectConfig<IWsMessage<any>>{
  url: string;

  constructor(url:string) {
    this.url = url;
  }
}
