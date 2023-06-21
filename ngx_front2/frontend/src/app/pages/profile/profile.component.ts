import {Component, OnDestroy, OnInit} from '@angular/core';
import {EProfileWebsocketEvents} from "./EProfileWebsocketEvents";
import {WebsocketService} from "../../services/ws/websocket";
import {WSUserApi} from "../../services/API/WSUserApi";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit,OnDestroy{
  userApi:WSUserApi = new WSUserApi();
  constructor(private wsService:WebsocketService,) {
  }
  ngOnInit(): void {
    this.wsService.connect(this.userApi.getProfile);
    this.wsService.status.subscribe({next:value => {
        if(value){
          this.wsService.send(EProfileWebsocketEvents.GET_PROFILE,'');
          this.wsService.send(EProfileWebsocketEvents.PASSWORD_STRENGTH,'');
        }
      }})
  }
  ngOnDestroy(): void {
    this.wsService.wsSubject?.unsubscribe();
  }

}
