import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WebsocketService} from "./websocket.service";
import {rxStompServiceFactory} from "./ws-service-factory";



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers:[
    {
      provide: WebsocketService,
      useFactory: rxStompServiceFactory
    }
  ]
})
export class StompModule { }
