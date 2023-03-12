import {Component, OnInit} from '@angular/core';
import {EventBusService} from "../../shared/service/eventBus/event-bus.service";
import {AppEvents} from "../../shared/service/eventBus/EventData";

@Component({
  selector: 'app-hamburger',
  templateUrl: './hamburger.component.html',
  styleUrls: ['./hamburger.component.scss']
})
export class HamburgerComponent implements OnInit {
  state:boolean=true;

  constructor(private  eventBus:EventBusService) { }

  ngOnInit(): void {
  }

  onChangeEvent(): void {
    console.log(this.state);
    this.eventBus.emit({name:AppEvents.CHANGE_VISIBLE_SIDEBAR,value:this.state})
  }
}
