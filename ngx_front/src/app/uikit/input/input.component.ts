import {Component, Input, OnInit} from '@angular/core';
import {InputDefinition, InputType} from "./InputDefinition";
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {AppEvents} from "../../services/eventBus/EventData";


@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {
  @Input() definition:InputDefinition = new InputDefinition('','');
  constructor(private eventBus:EventBusService) { }

  ngOnInit(): void {
    if(this.definition.type===InputType.PASSWORD){
      this.eventBus.on(AppEvents.INPUT_SHOW_HIDE_PASSWORD,(action:{state:boolean,id:string[]})=>{
        if(!action.id.includes(this.definition.id)){
          return;
        }
        if(action.state){
          this.definition.type=InputType.TEXT
        }else {
          this.definition.type=InputType.PASSWORD;
        }
      })
    }

  }

}
