import {Component, Input, OnInit} from '@angular/core';
import {InputDefinition} from "./InputDefinition";


@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {
  @Input() definition:InputDefinition = new InputDefinition();
  constructor() { }

  ngOnInit(): void {
  }

}
