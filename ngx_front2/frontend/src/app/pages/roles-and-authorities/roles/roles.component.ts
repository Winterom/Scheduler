import {Component, OnInit} from '@angular/core';
import {TreeNode} from "primeng/api";

import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import RolesGroup = RolesAuthoritiesInterfaces.RolesGroup;
import Role = RolesAuthoritiesInterfaces.Role;
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {RolesEvents} from "../RolesEvents";


@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit{
  roles: TreeNode[]=[];

  constructor(private wsService:WebsocketService,
              private eventBus:EventBusService) {
  }
  ngOnInit(): void {
    this.wsService.on<RolesGroup>(ERolesWebsocketEvents.ALL_ROLES).subscribe({
      next: data => {

         const  rawRoles = data.data.roles.filter(x=> x.label!='ROOT');
         this.walkTheTree(rawRoles);
        this.roles = rawRoles;
      }});

  }

  walkTheTree(nodes:Role[]){
    nodes.forEach((node)=>{
      if(node.isCatalog){
        this.createCatalog(node);
        if(node.children&&node.children.length>0){
          let childes:Role[] = node.children as Role[];
          this.walkTheTree(childes);
        }
      }else{
        this.createItem(node);
      }
    })
  }

  createCatalog(role:Role){
    role.collapsedIcon = 'fa fa-folder';
    role.expandedIcon = 'fa fa-folder-open-o';
  }
  createItem(role:Role){
    role.icon = 'fa fa-tags';
  }

  nodeSelect($event: any) {
    this.eventBus.emit({name:RolesEvents.ROLE_SELECT.toString(), value:$event});
  }
}

