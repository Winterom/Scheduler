import {ModuleWithProviders, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {WebSocketConfig} from "./websocket.interfaces";
import { config } from './websocket.config';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class WebsocketModule {
  public static config(wsConfig: WebSocketConfig): ModuleWithProviders<WebsocketModule> {
    return {
      ngModule: WebsocketModule,
      providers: [{ provide: config, useValue: wsConfig }]
    };
  }
}
