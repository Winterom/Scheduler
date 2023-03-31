import {Injectable, OnDestroy} from "@angular/core";
import * as SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements  OnDestroy{
  socket = new SockJS("");
  stompClient = Stomp.over(this.socket);

  constructor() {}

  ngOnDestroy(): void {
  }



}
