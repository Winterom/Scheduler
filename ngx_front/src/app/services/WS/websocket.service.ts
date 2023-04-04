import {Injectable} from "@angular/core";
import {RxStomp} from "@stomp/rx-stomp";
import {WSConfig} from "./WSConfig";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService extends RxStomp {
  private config = new WSConfig();

  constructor() {
    super();
    super.configure(this.config);
    super.activate();
  }





}
