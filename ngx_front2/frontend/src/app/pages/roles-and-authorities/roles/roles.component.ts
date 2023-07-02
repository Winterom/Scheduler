import {Component, OnInit} from '@angular/core';
import {TreeNode} from "primeng/api";

import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {RolesEvents} from "../RolesEvents";
import {FormControl, FormGroup} from "@angular/forms";
import RolesGroup = RolesAuthoritiesInterfaces.RolesGroup;
import Role = RolesAuthoritiesInterfaces.Role;
import SelectedStatus = RolesAuthoritiesInterfaces.SelectedStatus;
import RoleStatus = RolesAuthoritiesInterfaces.RoleStatus;


@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit{
  roles: TreeNode[]=[];
  filterForm: FormGroup;
  statusControl:FormControl;
  selStatus: SelectedStatus[] = [
    {name: 'Любые', code:null},
    {name: 'Активные', code: RoleStatus.ACTIVE},
    {name: 'Удаленные', code: RoleStatus.DELETE},
    {name: 'Приостановлено', code: RoleStatus.PASSED}
  ];

  constructor(private wsService:WebsocketService,
              private eventBus:EventBusService) {
    this.statusControl = new FormControl<any>('')
    this.filterForm = new FormGroup<any>({
      statusControl: this.statusControl,
    })
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

