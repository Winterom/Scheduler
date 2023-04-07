import {Injectable} from "@angular/core";
import {RxStomp} from "@stomp/rx-stomp";
import {rxStompConfig} from "./WSConfig";
import {UserService} from "../auth/user.service";
import {RootWSApi} from "../API/RootWSApi";



@Injectable({
  providedIn: 'root'
})
export class WebsocketService extends RxStomp {
  private readonly config;

  constructor(private user:UserService) {
    super();
    const api = new RootWSApi();
    rxStompConfig.brokerURL = api.getWSRootApi();
    if(this.user.token!=null){
      const tok = 'Bearer '+this.user.token;
      rxStompConfig.connectHeaders={
        'Authorization': tok
      }
    }
    this.config = rxStompConfig;
    super.configure(this.config);
    super.activate();
  }

}
