import {Component, OnInit} from '@angular/core';
import {InputDefinition} from "../../../../uikit/input/InputDefinition";
import {
  SORT_DIRECTION,
  TableDefinition,
  TableHeaderColumnDefinition, TableRows,
  VALUE_TYPE
} from "../../../../uikit/table/TableDefinition";
import {EventBusService} from "../../../../services/eventBus/event-bus.service";
import {AppEvents} from "../../../../services/eventBus/EventData";
import {EmailsService, EmailsSortParam} from "./emails.service";



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
      {title: EmailTableTitle.EMAIL, sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '20%'},
      {title: EmailTableTitle.DESTINATION, sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '20%'},
      {title: EmailTableTitle.USAGE, sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '15%'},
      {title: EmailTableTitle.DESCRIPTION, sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '40%'}
    ];
    this.emailTable.tableID='emails_property_table';
    this.emailTable.rows = [];
  }

  ngOnInit(): void {
    this.eventBus.on(AppEvents.TABLE_COLUMN_CHANGE_SORT, (id:string)=>{
      if(id!=this.emailTable.tableID){
        return;
      }
      this.onChangeDirection();
    })
    const param: EmailsSortParam[]= [{
      header: EmailTableTitle.EMAIL,
      direction: this.getSortDirection(EmailTableTitle.EMAIL)
    },{
      header:EmailTableTitle.USAGE,
      direction: this.getSortDirection(EmailTableTitle.USAGE)
    }]
    this.emailsService.emailList(param).subscribe({next:data=>{
        this.emailTable.rows = []
        data.rows.forEach((value)=>{

        });
      }});
  }

  onChangeDirection(){
    console.log('Изменилась сортировка');

  }

  getSortDirection(title:string):SORT_DIRECTION {
    const header = this.emailTable.headerDefinition.find(elem => elem.title === title);
    // @ts-ignore
    return header.sort;
  }
}
export enum EmailTableTitle{
  EMAIL='Email',
  DESTINATION='Назначение',
  USAGE = 'Используется',
  DESCRIPTION = 'Описание'
}
