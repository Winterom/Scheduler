import { Component } from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {TableDefinition} from "../../../../uikit/table/TableDefinition";
import {WebsocketService} from "../../../../services/WS/websocket.service";
import {EmailsWSApi} from "./EmailsWSApi";
import {Subscription} from "rxjs";



@Component({
  selector: 'app-mail-settings',
  templateUrl: './mail-settings.component.html',
  styleUrls: ['./mail-settings.component.scss']
})
export class MailSettingsComponent {
  public mailInput:InputDefinition = new InputDefinition('email','email');
  public passwordInput:InputDefinition = new InputDefinition('password','пароль');
  public emailTable:TableDefinition = new TableDefinition();
  private api:EmailsWSApi = new EmailsWSApi();
  private topicSubscription: Subscription;
  constructor(private ws:WebsocketService) {
    this.topicSubscription = ws.watch('/events/emails').subscribe((message:any)=>{
      console.log(message)
    });
    const url = this.api.getEmailsListApi();
    console.log(url);
    ws.publish({destination: url})
  }

}
