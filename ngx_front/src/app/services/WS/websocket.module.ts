import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {WebsocketService} from "./websocket.service";




@NgModule({
  declarations: [],
  providers:[WebsocketService],
  imports: [
    CommonModule
  ]
})
export class WebsocketModule {
}
