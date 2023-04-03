import {Injectable, OnDestroy} from "@angular/core";
import {Client, StompConfig} from "@stomp/stompjs";
import {RootWSApi} from "../API/RootWSApi";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements  OnDestroy{
  private config = new StompConfig();
  private _stompClient = new Client();
  private api:RootWSApi = new RootWSApi();

  constructor() {
    this._stompClient.debug = function (str) {
      console.log(str);
    };
    this._stompClient.onConnect= function (){
      console.log("ура подключились!")
    };
    this.config.brokerURL=this.api.getWSRootApi();
    this._stompClient.configure(this.config);
    this._stompClient.activate();
  }

  ngOnDestroy(): void {
    this._stompClient.deactivate().then(()=>{
      console.log("disconnected")
    });
  }


  get stompClient(): Client {
    return this._stompClient;
  }
}
