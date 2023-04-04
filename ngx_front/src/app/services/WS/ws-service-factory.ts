import {WebsocketService} from "./websocket.service";

export function rxStompServiceFactory() {
  return new WebsocketService();
}
