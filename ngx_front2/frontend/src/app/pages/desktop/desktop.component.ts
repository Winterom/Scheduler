import {Component, Inject, OnInit} from '@angular/core';
import { MenuItem } from 'primeng/api';


@Component({
  selector: 'app-desktop',
  templateUrl: './desktop.component.html',
  styleUrls: ['./desktop.component.scss']
})
export class DesktopComponent implements OnInit{
  items: MenuItem[]=[];
  position: string = 'left';
  constructor() {}

  ngOnInit(): void {
    this.items = [
      {
        label: 'Settings',
        icon: '../../../assets/images/settings_tools.png',
        tooltipOptions: {tooltipLabel:'Настройки приложения',tooltipPosition:'right'},
        routerLink:['/desktop/settings']
      },
      {
        label: 'Users',
        icon: '../../../assets/images/users.png',
        tooltipOptions: {tooltipLabel:'Управление пользователями',tooltipPosition:'right'}
      },
      {
        label: 'Roles and authorities',
        icon: '../../../assets/images/roles.png',
        tooltipOptions: {tooltipLabel:'Роли и права',tooltipPosition:'right'}
      }
    ];

  }

}
