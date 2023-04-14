import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {EventBusService} from "../../../../services/eventBus/event-bus.service";
import {AppEvents} from "../../../../services/eventBus/EventData";

@Component({
  selector: 'app-top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.scss']
})
export class TopMenuComponent  {

  constructor(private router:Router, private eventBus:EventBusService) { }

  logout(){
    this.eventBus.emit({name:AppEvents.LOGOUT,value:null});
    this.router.navigate(['login']);
  }
}
