import {Component, Input, OnInit} from '@angular/core';
import {CheckboxDefinition} from "./CheckboxDefinition";
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {AppEvents} from "../../services/eventBus/EventData";

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss']
})
export class CheckboxComponent implements OnInit{
  @Input() definition:CheckboxDefinition = new CheckboxDefinition('','');
  public state:boolean=false;
  constructor(private eventBus:EventBusService) { }

  onChange(event:any){
    this.definition.state = event.target.checked;
    this.definition.onChange();
  }

  ngOnInit(): void {
    this.eventBus.on(AppEvents.CHECK_BOX_CHANGE_STATE, (value:any)=>{
      if(this.definition.id!=value.id){
        return;
      }
      this.state=value.state;
    })
  }


}
