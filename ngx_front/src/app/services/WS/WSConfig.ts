import {RxStompConfig} from "@stomp/rx-stomp";
import {RootWSApi} from "../API/RootWSApi";

export class WSConfig extends RxStompConfig{
  private api:RootWSApi = new RootWSApi();
  constructor() {
    super();
    super.brokerURL=this.api.getWSRootApi();
    super.heartbeatIncoming= 0; // Typical value 0 - disabled
    super.heartbeatOutgoing= 20000; // Typical value 20000 - every 20 seconds
    super.debug=(msg:string)=>{
      console.log(msg);
    }
  }
}
