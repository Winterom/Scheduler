import {Component, Input, OnInit} from '@angular/core';
import {TableToolbarDefinition} from "./TableToolbarDefinition";

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

}
