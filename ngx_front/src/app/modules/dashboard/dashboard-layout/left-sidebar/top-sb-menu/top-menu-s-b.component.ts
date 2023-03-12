import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

export enum MENU_LEVEL{
  MAIN_MENU,
  ADMIN_MENU
}
export interface MenuRow{
  url:string;
  level:MENU_LEVEL;
}
const MenuMap:MenuRow[]=[{url:'desktop',level:MENU_LEVEL.MAIN_MENU},
  {url:'board',level:MENU_LEVEL.MAIN_MENU},
  {url:'profile',level:MENU_LEVEL.MAIN_MENU},
  {url:'settings',level:MENU_LEVEL.ADMIN_MENU},
  {url:'users',level:MENU_LEVEL.ADMIN_MENU},
  {url:'authority',level:MENU_LEVEL.ADMIN_MENU}
];
@Component({
  selector: 'app-top-sb-menu',
  templateUrl: './top-menu-s-b.component.html',
  styleUrls: ['./top-menu-s-b.component.scss']
})
export class TopMenuSBComponent implements OnInit {
  menuLevel=MENU_LEVEL;
  menuIndex:MENU_LEVEL;

  constructor(route: Router) {
   const urlSegment = route.url.split('/').pop();
   this.menuIndex=MENU_LEVEL.MAIN_MENU;
   if(urlSegment){
     const row = MenuMap.filter((value) => (value.url === urlSegment)).pop();
     console.log(row);
     if(row){
       this.menuIndex= row.level;
     }
   }
  }

  ngOnInit(): void {
  }

}
