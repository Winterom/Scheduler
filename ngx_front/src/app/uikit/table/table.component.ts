import {Component, Input} from '@angular/core';
import {SORT_DIRECTION, TableDefinition, TableHeaderColumnDefinition, TableRows, VALUE_TYPE} from "./TableDefinition";
import {CheckboxDefinition} from "../checkbox/CheckboxDefinition";
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {AppEvents} from "../../services/eventBus/EventData";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent{
  @Input() tableDefinition: TableDefinition = new TableDefinition();
  public checkBoxDefinition:CheckboxDefinition = new CheckboxDefinition('main','');
  private rowsCheckBoxDefinitions:Map<string,CheckboxDefinition>=new Map<string, CheckboxDefinition>();

  constructor(private eventBus:EventBusService) {
    this.checkBoxDefinition.onChange=()=>{
        this.rowsCheckBoxDefinitions.forEach((value)=>{
          const id:string = String(value.id);
          const state = this.checkBoxDefinition.state;
          this.eventBus.emit({name:AppEvents.CHECK_BOX_CHANGE_STATE,value:{id,state}});
        })
    }
  }
   getHeaderDefinition():Map<string,TableHeaderColumnDefinition>{
     return this.tableDefinition.headerDefinition;
  }

  changeSortDirection(title:string){
    const header = this.tableDefinition.headerDefinition.get(title);
    if(!header||header.sort===SORT_DIRECTION.NONE){
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

  completeRowCheckBoxDefinition(id:number):CheckboxDefinition{
    const definition = new CheckboxDefinition(String(id)+'_email','');
    this.rowsCheckBoxDefinitions.set(String(id)+'_email',definition)
    return definition;
  }

  isHeaderValueType(header:TableHeaderColumnDefinition):boolean{
    return header.type===VALUE_TYPE.BOOLEAN;
  }

  selectRow(row:TableRows){
      const activeRow = this.tableDefinition.rows.filter((value)=>{return value.isActive});
      if(activeRow.length>0){
        activeRow[0].isActive=false;
      }
      row.isActive=true;
  }
}
