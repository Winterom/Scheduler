import {Component, Input, OnInit} from '@angular/core';
import {ButtonDefinition} from "./ButtonDefinition";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {
  @Input() definition:ButtonDefinition = {
    background:'',
    backgroundHover:'',
    label:'',
    onClick() {
    }
  };
  constructor() { }

  ngOnInit(): void {
  }

}
