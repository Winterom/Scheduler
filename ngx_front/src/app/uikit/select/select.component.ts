import {Component, Input, OnInit} from '@angular/core';
import {ModalDialogDefinition} from "../modal-dialog/ModalDialogDefinition";
import {SelectDefinition} from "./SelectDefinition";

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss']
})
export class SelectComponent implements OnInit {
  @Input() definition: SelectDefinition = new SelectDefinition();
  constructor() { }

  ngOnInit(): void {
  }

}
