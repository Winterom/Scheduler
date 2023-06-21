import { Component } from '@angular/core';
import {WSUserApi} from "../../services/API/WSUserApi";
import {WebsocketService} from "../../services/ws/websocket";
import {ERolesWebsocketEvents} from "./ERolesWebsocketEvents";

@Component({
  selector: 'app-roles-and-authorities',
  templateUrl: './roles-and-authorities.component.html',
  styleUrls: ['./roles-and-authorities.component.scss']
})
export class RolesAndAuthoritiesComponent {

  userApi:WSUserApi = new WSUserApi();
  constructor(private wsService:WebsocketService,) {
  }
  ngOnInit(): void {
    this.wsService.connect(this.userApi.getRoles);
    this.wsService.status.subscribe({next:value => {
        if(value){
          this.wsService.send(ERolesWebsocketEvents.ALL_ROLES,'');
        }
      }})
  }
  ngOnDestroy(): void {
    this.wsService.wsSubject?.unsubscribe();
  }
}
