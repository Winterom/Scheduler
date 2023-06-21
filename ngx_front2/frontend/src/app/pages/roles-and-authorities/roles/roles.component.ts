import {Component, OnInit} from '@angular/core';
import {TreeNode} from "primeng/api";

import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import RolesGroup = RolesAuthoritiesInterfaces.RolesGroup;


@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss']
})
export class RolesComponent implements OnInit{
  roles: TreeNode[]=[];

  constructor(private wsService:WebsocketService) {
  }
  ngOnInit(): void {
    this.wsService.on<RolesGroup>(ERolesWebsocketEvents.ALL_ROLES).subscribe({next:data=>{
      console.log(data.data);
      }})
  }
}
