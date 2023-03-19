import {Component, Input, OnInit} from '@angular/core';
import {CheckboxDefinition} from "./CheckboxDefinition";

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss']
})
export class CheckboxComponent implements OnInit{
  @Input() definition:CheckboxDefinition = new CheckboxDefinition();
  constructor() { }

  onChange(){
    this.definition.onChange();
  }

  ngOnInit(): void {
  }
}
