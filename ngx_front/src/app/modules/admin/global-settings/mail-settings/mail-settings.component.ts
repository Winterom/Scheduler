import {Component, OnInit} from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {SORT_DIRECTION, TableDefinition, VALUE_TYPE} from "../../../../uikit/table/TableDefinition";
import {EventBusService} from "../../../../services/eventBus/event-bus.service";
import {AppEvents} from "../../../../services/eventBus/EventData";
import {EmailsService} from "./emails.service";


@Component({
  selector: 'app-mail-settings',
  templateUrl: './mail-settings.component.html',
  styleUrls: ['./mail-settings.component.scss']
})
export class MailSettingsComponent implements OnInit{
  public mailInput:InputDefinition = new InputDefinition('email','email');
  public passwordInput:InputDefinition = new InputDefinition('password','пароль');
  public emailTable:TableDefinition = new TableDefinition();

  constructor(private eventBus:EventBusService,private emailsService:EmailsService) {
    this.emailTable.isCheckColumn=true;
    this.emailTable.headerDefinition = [
      {title: 'email', sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '20%'},
      {title: 'Назначение', sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '20%'},
      {title: 'Используется', sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '15%'},
      {title: 'Описание', sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '40%'}
    ];
    this.emailTable.tableID='emails_property_table';
  }

  ngOnInit(): void {
    this.eventBus.on(AppEvents.TABLE_COLUMN_CHANGE_SORT, (id:string)=>{
      if(id!=this.emailTable.tableID){
        return;
      }
      this.onChangeDirection();
    })
    this.emailsService.emailList().subscribe();
  }

  onChangeDirection(){
    console.log('Изменилась сортировка');

  }
}
