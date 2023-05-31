import { Component } from '@angular/core';
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-settings',
  templateUrl: './widget-layout.component.html',
  styleUrls: ['./widget-layout.component.scss'],
  providers:[MessageService]
})
export class WidgetLayoutComponent {
  constructor(private eventBus:EventBusService,private router: Router) {
  }

  closeWidget(){
    this.router.navigate(['desktop']);
  }
}
