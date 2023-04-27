import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TableDefinition} from "../table/TableDefinition";
import {ModalDialogDefinition} from "./ModalDialogDefinition";

@Component({
  selector: 'app-modal-dialog',
  templateUrl: './modal-dialog.component.html',
  styleUrls: ['./modal-dialog.component.scss'],
})
export class ModalDialogComponent implements OnInit {
  isVisible:boolean = true;
  @Input() definition: ModalDialogDefinition = new ModalDialogDefinition();
  @Output() isConfirmed: EventEmitter<boolean> = new EventEmitter<boolean>();
  constructor() { }

  ngOnInit(): void {
  }

  changeVisible(event:any){
    this.isVisible=false;
  }
  private confirm() {
    this.isConfirmed.emit(true);
  }
  private close() {
    this.changeVisible(null);
    this.isConfirmed.emit(false);
  }
}
