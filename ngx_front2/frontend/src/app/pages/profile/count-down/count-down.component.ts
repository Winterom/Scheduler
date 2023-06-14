import {Component, Input, OnDestroy} from '@angular/core';
import {interval, Subscription} from "rxjs";
import {ChronosUtils} from "../../../shared/ChronosUtils";
import Chronos = ChronosUtils.Chronos;

import chronosToMilli = ChronosUtils.chronosToMilli;

@Component({
  selector: 'app-count-down',
  templateUrl: './count-down.component.html',
  styleUrls: ['./count-down.component.scss']
})
export class CountDownComponent implements OnDestroy{
  private subscription: Subscription;

  milliSecondsInASecond = 1000;
  @Input() public time:number=0;
  @Input() public chronos:Chronos = Chronos.MILLIS;
  public secondsToDday:number=0;
  public minutesToDday:number=0;
  public hoursToDday:number=0;
  public daysToDday:number=0;
  public timeDifference:number=0;

  constructor() {
    this.timeDifference = chronosToMilli(this.chronos);
    this.subscription = interval(1000)
      .subscribe(x => { this.getTimeDifference(); });
  }
  private getTimeDifference () {
      this.timeDifference = this.timeDifference-1000;
      this.allocateTimeUnits(this.timeDifference);
    }

  private allocateTimeUnits (timeDifference:number) {
      this.secondsToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond) % 60);
      this.minutesToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * 60) % 60);
      this.hoursToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * 60 * 60) % 24);
      this.daysToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * 60 * 60 * 24));
    }
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
