import {Component, Input, OnInit} from '@angular/core';
import {EToolbarButton, TableToolbarDefinition} from "./TableToolbarDefinition";

@Component({
  selector: 'app-table-toolbar',
  templateUrl: './table-toolbar.component.html',
  styleUrls: ['./table-toolbar.component.scss']
})
export class TableToolbarComponent implements OnInit {
  @Input() definition:TableToolbarDefinition =new TableToolbarDefinition();
  isHaveAddButton:boolean=false;
  isHaveDeleteButton:boolean=false;
  isHaveEditeButton:boolean=false;
  isHaveUpdateTableButton:boolean=false;
  constructor() { }

  ngOnInit(): void {
    this.isHaveAddButton = this.hasButton(EToolbarButton.ADD);
    this.isHaveDeleteButton = this.hasButton(EToolbarButton.DELETE);
    this.isHaveEditeButton = this.hasButton(EToolbarButton.EDIT);
    this.isHaveUpdateTableButton = this.hasButton(EToolbarButton.UPDATE);
  }

  private hasButton(type:EToolbarButton):boolean{
    return this.definition.buttons.has(type);
  }
}
