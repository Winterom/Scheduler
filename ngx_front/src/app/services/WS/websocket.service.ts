import {Injectable, OnDestroy} from "@angular/core";
import {Client, StompConfig} from "@stomp/stompjs";


@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements  OnDestroy{
  config = new StompConfig();
  stompClient = new Client();

  constructor() {
    this.config.brokerURL="ws://localhost:8080/application/ws";
    this.stompClient.configure(this.config);
    this.stompClient.activate();
  }

  ngOnDestroy(): void {
    this.stompClient.deactivate().then(()=>{
      console.log("disconnected")
    });
  }



}
