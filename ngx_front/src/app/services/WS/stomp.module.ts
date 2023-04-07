import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WebsocketService} from "./websocket.service";


@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers:[
    {
      provide: WebsocketService
    }
  ]
})
export class StompModule { }
