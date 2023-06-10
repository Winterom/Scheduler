import {Component, OnInit} from '@angular/core';
import {WSRequestEvents} from "../../../services/ws/websocket/WSRequestEvents";
import {WebsocketService} from "../../../services/ws/websocket";
import {WSUserApi} from "../../../services/API/WSUserApi";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit{
  userApi:WSUserApi = new WSUserApi();
  constructor(private wsService:WebsocketService,) {
  }
  ngOnInit(): void {
    this.wsService.connect(this.userApi.getProfile);
    this.wsService.status.subscribe({next:value => {
        if(value){
          this.wsService.send(WSRequestEvents.GET_PROFILE,'');
          this.wsService.send(WSRequestEvents.PASSWORD_STRENGTH,'');
        }
      }})
  }

}
