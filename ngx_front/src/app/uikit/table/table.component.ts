import {Component, Input} from '@angular/core';
import {SORT_DIRECTION, TableDefinition, TableHeaderColumnDefinition} from "./TableDefinition";
import {CheckboxDefinition} from "../checkbox/CheckboxDefinition";
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {AppEvents} from "../../services/eventBus/EventData";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent {
  @Input() tableDefinition: TableDefinition = new TableDefinition();
  public checkBoxDefinition:CheckboxDefinition = new CheckboxDefinition('main','');
  constructor(private eventBus:EventBusService) {

  }
   getHeaderDefinition():TableHeaderColumnDefinition[]{
     return this.tableDefinition.headerDefinition;
  }

  changeSortDirection(index:number){
    const header = this.tableDefinition.headerDefinition[index];
    if(header.sort===SORT_DIRECTION.NONE){
      return;
    }

    this.eventBus.emit({name:AppEvents.TABLE_COLUMN_CHANGE_SORT,
      value:this.tableDefinition.tableID})
    if(header.sort===SORT_DIRECTION.DESC){
      header.sort=SORT_DIRECTION.ASC;
    }else {
      header.sort=SORT_DIRECTION.DESC;
    }
  }

  getRowCheckBoxDefinition(id:number):CheckboxDefinition{
    return new CheckboxDefinition(String(id),'');
  }
}
