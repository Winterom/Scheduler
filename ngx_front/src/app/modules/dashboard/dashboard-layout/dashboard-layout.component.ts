import {Component, OnInit} from '@angular/core';
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {AppEvents} from "../../../services/eventBus/EventData";


@Component({
  selector: 'app-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent implements OnInit {
  sideBarVisible:boolean=true;
  constructor(private eventBus:EventBusService) { }

  ngOnInit(): void {
    this.eventBus.on(AppEvents.CHANGE_VISIBLE_SIDEBAR,
      (state: boolean)=>{
      this.sideBarVisible= state})
  }

}

