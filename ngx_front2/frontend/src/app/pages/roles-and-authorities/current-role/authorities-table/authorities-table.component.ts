import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {RolesAuthoritiesInterfaces} from "../../../../types/roles/RolesAuthoritiesInterfaces";
import AuthorityNode = RolesAuthoritiesInterfaces.AuthorityNode;
import {TreeNode} from "primeng/api";
import {WebsocketService} from "../../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../../ERolesWebsocketEvents";
import Authorities = RolesAuthoritiesInterfaces.Authorities;
interface Column {
  field: string;
  header: string;
}
@Component({
  selector: 'app-authorities-table',
  templateUrl: './authorities-table.component.html',
  styleUrls: ['./authorities-table.component.scss']
})
export class AuthoritiesTableComponent implements OnInit{

  authorities: AuthorityNode[]=[];
  columns: Column[] = [
    {field: 'key', header: 'ID'},
    {field: 'label', header: 'Название'},
    {field: 'description',header:'Описание'}
  ];
  selectedNodes!: TreeNode[];

  constructor(public ref:DynamicDialogRef,
              public wsService: WebsocketService,
              private config: DynamicDialogConfig) {
  }
  ngOnInit(): void {
    this.wsService.send(ERolesWebsocketEvents.ALL_AUTHORITIES,'')
    this.selectedNodes = this.config.data;
    this.wsService.on<Authorities>(ERolesWebsocketEvents.ALL_AUTHORITIES).subscribe({next:value=>{
      console.log(value.data)
      }})
  }

}
