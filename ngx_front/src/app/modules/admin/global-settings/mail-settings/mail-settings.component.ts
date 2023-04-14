import {Component, OnInit} from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {
  SORT_DIRECTION,
  TableDefinition,
  VALUE_TYPE
} from "../../../../uikit/table/TableDefinition";


@Component({
  selector: 'app-mail-settings',
  templateUrl: './mail-settings.component.html',
  styleUrls: ['./mail-settings.component.scss']
})
export class MailSettingsComponent implements OnInit{
  public mailInput:InputDefinition = new InputDefinition('email','email');
  public passwordInput:InputDefinition = new InputDefinition('password','пароль');
  public emailTable:TableDefinition = new TableDefinition();

  constructor() {
    this.emailTable.isCheckColumn=true;
    this.emailTable.headerDefinition = [
      {title: 'email', sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '20%'},
      {title: 'Назначение', sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '20%'},
      {title: 'Используется', sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '15%'},
      {title: 'Описание', sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '40%'}
    ];
  }

  ngOnInit(): void {

  }

}
