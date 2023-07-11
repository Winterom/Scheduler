import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../../../services/ws/websocket";
import {ERolesWebsocketEvents} from "../ERolesWebsocketEvents";
import {DynamicDialogConfig} from "primeng/dynamicdialog";
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import CheckedRoleForDelete = RolesAuthoritiesInterfaces.CheckedRoleForDelete;
import CheckedRole = RolesAuthoritiesInterfaces.CheckedRole;
import {UserInterfaces} from "../../../types/user/UserInterfaces";
import EUserStatus = UserInterfaces.EUserStatus;


@Component({
  selector: 'app-delete-role',
  templateUrl: './delete-role.component.html',
  styleUrls: ['./delete-role.component.scss']
})
export class DeleteRoleComponent implements OnInit {

  isDeletePossible: boolean = false;
  checkedRole: CheckedRoleForDelete | null = null;
  activeIndex: number = 0;
  roles: CheckedRole[] = [];
  deleteRole: CheckedRole|undefined = undefined;

  constructor(private wsService: WebsocketService,
              private dialogConfig: DynamicDialogConfig) {
  }

  ngOnInit() {
    this.deleteRole = this.dialogConfig.data;
    this.wsService.send(ERolesWebsocketEvents.CHECK_ROLE_FOR_DELETE, {roleId: this.deleteRole?.key});
    this.wsService.on<CheckedRoleForDelete>(ERolesWebsocketEvents.CHECK_ROLE_FOR_DELETE).subscribe({
      next: data => {
        this.checkedRole = data.data;
        this.checkedRole.roleAssignedUsers.forEach(x => {
          if (!x.isCatalog && x.users.length > 0) {
            this.roles.push(x);
          }
        })
        if (this.roles.length === 0) {
          this.isDeletePossible = true;
        }
      }
    })
  }


  deleteRoleReq() {

  }

  getSeverity(status: EUserStatus):string {
    switch (status) {
      case EUserStatus.DELETED:
        return 'danger';
      case EUserStatus.ACTIVE:
        return 'success';
      case EUserStatus.BANNED:
        return 'danger';
      case EUserStatus.DISMISSED:
        return 'warning';
      default:
        return 'warning'
    }
  }

  getStatus(status: EUserStatus):string {
    switch(status){
      case EUserStatus.DELETED:  return 'Удален';
      case EUserStatus.ACTIVE:  return 'Активный';
      case EUserStatus.BANNED:  return 'Заблокирован';
      case EUserStatus.DISMISSED:  return 'Уволен';
      default: return 'Новый пользователь'
    }
  }

  getHeader(label: string):string {
    return 'Роль: '+ label;
  }

  protected readonly undefined = undefined;
}
