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
    this.emailTable.headerDefinition.set(EmailTableTitle.EMAIL,{sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.STRING, width: '20%',align:'center'});
    this.emailTable.headerDefinition.set(EmailTableTitle.DESTINATION,{sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '20%',align:'center'});
    this.emailTable.headerDefinition.set(EmailTableTitle.USAGE,{sort: SORT_DIRECTION.ASC, type: VALUE_TYPE.BOOLEAN, width: '15%',align:'center'});
    this.emailTable.headerDefinition.set(EmailTableTitle.DESCRIPTION,{sort: SORT_DIRECTION.NONE, type: VALUE_TYPE.STRING, width: '40%',align:'left'})
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
        const rows = new Set<EmailTableRow>;
        const headers = this.emailTable.headerDefinition;
        data.forEach(function (value){
          let row = new EmailTableRow(value.id)
          row.alias = value.alias;
          row.email_type = value.type;
          const emailH = headers.get(EmailTableTitle.EMAIL)
          if(emailH){
            row.cells.set(emailH,value.email)
          }
          const destH = headers.get(EmailTableTitle.DESTINATION);
          if(destH){
            row.cells.set(destH,value.type)
          }
          const enabledH = headers.get(EmailTableTitle.USAGE);
          if(enabledH){
            row.cells.set(enabledH,value.isEnabled)
          }
          const descH = headers.get(EmailTableTitle.DESCRIPTION);
          if(descH){
            row.cells.set(descH,value.description)
          }
          rows.add(row);
        })
      this.emailTable.rows=Array.from(rows);
      console.log(rows)
      }});
  }

  onChangeDirection(){
    console.log('Изменилась сортировка');

  }

  getSortDirection(title:EmailTableTitle):SORT_DIRECTION {
    const header = this.emailTable.headerDefinition.get(title);
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

class EmailTableRow extends TableRows{
  cells: Map<TableHeaderColumnDefinition, string | number | boolean> = new Map<TableHeaderColumnDefinition, string | number | boolean>();
  id: number;
  email_type:string='';
  alias:string='';
  constructor(id:number) {
    super();
    this.id = id;
  }
}
