import {Component, OnInit} from '@angular/core';
import {MessageService, TreeDragDropService, TreeNode} from "primeng/api";

import {ResponseStatus, WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {RolesEvents} from "../RolesEvents";
import {FormControl, FormGroup} from "@angular/forms";
import RolesGroup = RolesAuthoritiesInterfaces.RolesGroup;
import Role = RolesAuthoritiesInterfaces.Role;
import SelectedStatus = RolesAuthoritiesInterfaces.SelectedStatus;
import RoleStatus = RolesAuthoritiesInterfaces.RoleStatus;
import DragDropRole = RolesAuthoritiesInterfaces.DragDropRole;
import {CustomMessage} from "../../../shared/messages/CustomMessages";
import addErrorMessage = CustomMessage.addErrorMessage;


@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss'],
  providers: [TreeDragDropService, MessageService]
})
export class RolesComponent implements OnInit{
  roles: Role[]=[];
  expandedNodes: Map<string,TreeNode>= new Map;
  selected:Role|null=null;
  filterForm: FormGroup;
  statusControl:FormControl;
  selStatus: SelectedStatus[] = [
    {name: 'Любые', code:null},
    {name: 'Активные', code: RoleStatus.ACTIVE},
    {name: 'Удаленные', code: RoleStatus.DELETE},
    {name: 'Приостановлено', code: RoleStatus.PASSED}
  ];

  constructor(private wsService:WebsocketService,
              private eventBus:EventBusService,
              private messageService:MessageService) {
    this.statusControl = new FormControl<any>('')
    this.filterForm = new FormGroup<any>({
      statusControl: this.statusControl,
    })
  }
  ngOnInit(): void {
    this.wsService.on<RolesGroup>(ERolesWebsocketEvents.ALL_ROLES).subscribe({
      next: data => {
        if(data.responseStatus===ResponseStatus.ERROR){
          data.errorMessages.forEach(x=>{
            addErrorMessage(this.messageService,x,null);
          })
        }
         const  rawRoles = data.data.roles
         this.walkTheTree(rawRoles);
         this.roles = rawRoles;
      }});

  }

  walkTheTree(nodes:Role[]){
    nodes.forEach((node)=>{
      if(node.isCatalog){
        if(node.key&&this.expandedNodes.has(node.key)){
          node.expanded=true;
        }
        this.setCatalogStyles(node);
        if(node.children&&node.children.length>0){
          let childes:Role[] = node.children as Role[];
          this.walkTheTree(childes);
        }
      }else{
        this.setLeafStyles(node);
      }
    })
  }

  setCatalogStyles(role:Role){
    role.collapsedIcon = 'fa fa-folder';
    role.expandedIcon = 'fa fa-folder-open-o';
  }

  setLeafStyles(role:Role){
    role.icon = 'fa fa-tags';
  }

  nodeSelect($event: any) {
    this.eventBus.emit({name:RolesEvents.ROLE_SELECT.toString(), value:$event});
  }

  nodeDrop(event: any) {
    const message: DragDropRole = {newParentId:event.dropNode.key,roleId:event.dragNode.key};
    this.expandedNodes = new Map<string, TreeNode>();
    this.completeExpandedNode(this.roles);
    this.wsService.send(ERolesWebsocketEvents.ROLE_DRAG_DROP,message);
  }

  completeExpandedNode(nodes:Role[]){
    nodes.forEach(node=>{
      if(node.isCatalog){
        if(node.key&&node.expanded){
          this.expandedNodes.set(node.key,node)
        }
        if(node.children&&node.children.length>0){
          this.completeExpandedNode(node.children as Role[])
        }
      }
    })
  }

  deleteRole() {
    console.log(this.selected)
  }

}

