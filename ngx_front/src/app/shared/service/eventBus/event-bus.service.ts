import { Injectable } from '@angular/core';
import {filter, map, Subject} from "rxjs";
import {AppEvents, EventData} from "./EventData";

@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  private subject$ = new Subject()
  constructor() { }

  emit(event:EventData){
    this.subject$.next(event);
  }

  on(eventName:AppEvents,action:any){
    return this.subject$.pipe(
      filter<any>((e:EventData)=>e.name===eventName),
      map((e:EventData)=>e.value)).subscribe(action);
  }
}
