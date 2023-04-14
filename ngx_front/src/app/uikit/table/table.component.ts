import {Component, Input} from '@angular/core';
import {SORT_DIRECTION, TableDefinition, TableHeaderColumnDefinition} from "./TableDefinition";
import {CheckboxDefinition} from "../checkbox/CheckboxDefinition";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent {
  @Input() tableDefinition: TableDefinition = new TableDefinition();
  public checkBoxDefinition:CheckboxDefinition = new CheckboxDefinition('main','');
  constructor() {

  }
   getHeaderDefinition():TableHeaderColumnDefinition[]{
     return this.tableDefinition.headerDefinition;
  }

  changeSortDirection(index:number){
    const header = this.tableDefinition.headerDefinition[index];
    if(header.sort===SORT_DIRECTION.NONE){
      return;
    }
    if(header.sort===SORT_DIRECTION.DESC){
      header.sort=SORT_DIRECTION.ASC;
    }else {
      header.sort=SORT_DIRECTION.DESC;
    }
  }
}
