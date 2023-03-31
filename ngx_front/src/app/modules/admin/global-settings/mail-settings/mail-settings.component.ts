import { Component, OnInit } from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {TableDefinition} from "../../../../uikit/table/TableDefinition";

@Component({
  selector: 'app-mail-settings',
  templateUrl: './mail-settings.component.html',
  styleUrls: ['./mail-settings.component.scss']
})
export class MailSettingsComponent implements OnInit {
  public mailInput:InputDefinition = new InputDefinition('email','email');
  public passwordInput:InputDefinition = new InputDefinition('password','пароль');
  public emailTable:TableDefinition = new TableDefinition();
  constructor() { }

  ngOnInit(): void {

  }

}
