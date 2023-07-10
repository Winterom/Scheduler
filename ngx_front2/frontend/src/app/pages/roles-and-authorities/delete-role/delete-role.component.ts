import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {DynamicDialogConfig} from "primeng/dynamicdialog";

@Component({
  selector: 'app-delete-role',
  templateUrl: './delete-role.component.html',
  styleUrls: ['./delete-role.component.scss']
})
export class DeleteRoleComponent implements OnInit{
  stepItems: MenuItem[] =[];
  activeIndex: number = 0;
  disabledNextBtn: boolean =false;
  disabledBackBtn: boolean = true;

  constructor(private wsService: WebsocketService, private dialogConfig:DynamicDialogConfig) {
  }
  ngOnInit() {
    this.stepItems = [{label: 'Правила удаления'},
      {label: 'Контроль'},
      {label: 'Удаление'},];
    const roleId = this.dialogConfig.data
    this.wsService.send(ERolesWebsocketEvents.CHECK_ROLE_FOR_DELETE,{roleId:roleId});
  }

  onActiveIndexChange($event: any) {
    this.activeIndex = $event;
  }

  nextStep() {
      if(this.activeIndex===0){
        this.activeIndex = 1;
        this.disabledBackBtn =false;
      }else {
        this.activeIndex=2;
      }

  }

  backStep() {
    if(this.activeIndex===1){
      this.activeIndex=0;
      this.disabledBackBtn=true;
    }
  }

  deleteRole() {

  }
}
