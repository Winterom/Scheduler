import {Component, OnDestroy, OnInit} from '@angular/core';
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {RolesEvents} from "../RolesEvents";
import Role = RolesAuthoritiesInterfaces.Role;
import RoleStatus = RolesAuthoritiesInterfaces.RoleStatus;
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {AuthoritiesTableComponent} from "./authorities-table/authorities-table.component";
import AuthorityNode = RolesAuthoritiesInterfaces.AuthorityNode;
import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {MessageService} from "primeng/api";
import {CustomMessage} from "../../../shared/messages/CustomMessages";
import addErrorMessage = CustomMessage.addErrorMessage;
import Authorities = RolesAuthoritiesInterfaces.Authorities;

@Component({
  selector: 'app-current-role',
  templateUrl: './current-role.component.html',
  styleUrls: ['./current-role.component.scss'],
  providers: [DialogService,MessageService]
})
export class CurrentRoleComponent implements OnInit, OnDestroy{
  role:Role|undefined = undefined;
  roleStatus: string='';
  labelControl:FormControl;
  descriptionControl:FormControl;
  roleForm: FormGroup;
  authorities: AuthorityNode[]=[];
  titleLabel:string = '';
  descriptionLabel:string = '';
  authoritiesFrmRef: DynamicDialogRef | undefined;

  constructor(private eventBus:EventBusService,
              public dialogService: DialogService,
              private wsService:WebsocketService,
              private messageService:MessageService) {
    this.labelControl = new FormControl<string>('', [Validators.required]);
    this.descriptionControl = new FormControl<string>('',[Validators.required]);
    this.roleForm = new FormGroup<any>({
      labelControl: this.labelControl,
      descriptionControl: this.descriptionControl
    })
  }
  ngOnInit(): void {

    this.eventBus.on(RolesEvents.ROLE_SELECT,(value:any)=>{
      if(this.role?.key===value.node.key){
        console.log('уже было')
        return;
      }
      this.role = value.node;
      if(!this.role?.isCatalog){
        this.wsService.send(ERolesWebsocketEvents.AUTHORITIES_BY_ROLE_ID,{roleId: this.role?.key});
      }
      this.labelControl.setValue(this.role?.label);
      this.descriptionControl.setValue(this.role?.description);
      if(this.role?.isCatalog){
        this.titleLabel = 'Название каталога';
        this.descriptionLabel = 'Описание каталога';
      }else {
        this.titleLabel = 'Название роли';
        this.descriptionLabel = 'Описание роли';
      }
      this.wsService.on<Authorities>(ERolesWebsocketEvents.AUTHORITIES_BY_ROLE_ID)
        .subscribe({next:data=>{
          if (data.responseStatus==='OK'){
            this.authorities = data.data.authorities;
          }else{
            data.errorMessages.forEach(x=>{
              addErrorMessage(this.messageService,x,null)
            })
          }
          }})
      switch (this.role?.status){
        case RoleStatus.ACTIVE: this.roleStatus='АКТИВНЫЙ'; return;
        case RoleStatus.DELETE: this.roleStatus = 'Роль (Каталог) удалена'; return;
        case RoleStatus.PASSED: this.roleStatus = 'Действие роли (каталога ролей) приостановлено'; return;
      }
    })
  }
  statusClasses():string {
    switch (this.role?.status){
      case RoleStatus.DELETE: return 'text-purple-400';
      case RoleStatus.PASSED: return 'text-orange-400';
      case RoleStatus.ACTIVE: return 'text-green-400';
      default: return 'text-green-400';
    }
  }

  submitRoleFrm() {

  }

  openAuthoritiesFrm() {
    this.authoritiesFrmRef = this.dialogService.open(AuthoritiesTableComponent,{
      data: this.authorities,
      draggable: true,
      resizable: false,
      modal: true,
      position: 'center',
      header: 'Выберите права для роли: '+this.role?.label
    })
    this.authoritiesFrmRef.onClose.subscribe({next: auth =>{
      if(auth!=null){
        this.authorities=auth;
      }
      }})
  }

  ngOnDestroy(): void {
    if(this.authoritiesFrmRef){
      this.authoritiesFrmRef.close();
    }
  }
}
