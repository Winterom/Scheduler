import { Component, OnInit } from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {TableDefinition} from "../../../../uikit/table/TableDefinition";
import {WebsocketService} from "../../../../services/WS/websocket.service";
import {EmailsWSApi} from "./EmailsWSApi";



@Component({
  selector: 'app-mail-settings',
  templateUrl: './mail-settings.component.html',
  styleUrls: ['./mail-settings.component.scss']
})
export class MailSettingsComponent implements OnInit {
  public mailInput:InputDefinition = new InputDefinition('email','email');
  public passwordInput:InputDefinition = new InputDefinition('password','пароль');
  public emailTable:TableDefinition = new TableDefinition();
  private api:EmailsWSApi = new EmailsWSApi();
  constructor(private ws:WebsocketService) { }

  ngOnInit(): void {
    this.ws.stompClient.subscribe(this.api.getEmailsListApi(),function(message) {
      console.log(message);
    });
  }

}
