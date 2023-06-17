import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {interval, Subscription, take, takeUntil} from "rxjs";
import {ChronosUtils} from "../../../shared/ChronosUtils";
import Chronos = ChronosUtils.Chronos;

import chronosToMilli = ChronosUtils.chronosToMilli;

@Component({
  selector: 'app-count-down',
  templateUrl: './count-down.component.html',
  styleUrls: ['./count-down.component.scss']
})
export class CountDownComponent implements OnInit,OnDestroy{
  private subscription: Subscription|null=null;

  milliSecondsInASecond = 1000;
  @Input() public time:number=0;
  @Output() onFinish:EventEmitter<boolean> = new EventEmitter();
  public secondsToDday:number=0;
  public minutesToDday:number=0;
  public hoursToDday:number=0;
  public daysToDday:number=0;
  public timeDifference:number=0;



  private getTimeDifference () {
    console.log(this.timeDifference)
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
    if(this.subscription!==null){
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    let finishDate = new Date(this.time);
    this.timeDifference = finishDate.getTime()-Date.now();
    console.log(this.time);
    console.log('finishdate: '+finishDate);
    console.log('difference: '+this.timeDifference/1000)
    if(this.timeDifference<0){
      this.onFinish.emit(true);
      return;
    }
    this.subscription = interval(1000).pipe(take(this.timeDifference/1000))
      .subscribe({next:t=>{
          this.getTimeDifference();
        },complete:()=>{
        this.onFinish.emit(true);
        }});
  }
}
