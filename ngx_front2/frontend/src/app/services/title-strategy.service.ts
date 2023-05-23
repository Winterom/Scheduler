import { Injectable } from '@angular/core';
import {RouterStateSnapshot, TitleStrategy} from "@angular/router";
import {Title} from "@angular/platform-browser";

@Injectable({
  providedIn: 'root'
})
export class TitleStrategyService extends TitleStrategy{

  constructor(private readonly title: Title) {
    super();
  }

  updateTitle(routerState: RouterStateSnapshot): void {
    const title = this.buildTitle(routerState);
    if (title !== undefined) {
      this.title.setTitle(`My Application | ${title}`);
    }
  }
}
