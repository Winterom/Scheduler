import {Component, Input, OnInit} from '@angular/core';
import {EToolbarButton, TableToolbarDefinition} from "./TableToolbarDefinition";

@Component({
  selector: 'app-table-toolbar',
  templateUrl: './table-toolbar.component.html',
  styleUrls: ['./table-toolbar.component.scss']
})
export class TableToolbarComponent implements OnInit {
  @Input() definition:TableToolbarDefinition =new TableToolbarDefinition();
  constructor() { }

  ngOnInit(): void {
  }

  hasButton(type:EToolbarButton):boolean{
    return this.definition.buttons.has(type);
  }
}
