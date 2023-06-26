import {Component, OnInit} from '@angular/core';
import {RolesAuthoritiesInterfaces} from "../../../types/roles/RolesAuthoritiesInterfaces";
import {EventBusService} from "../../../services/eventBus/event-bus.service";
import {RolesEvents} from "../RolesEvents";
import Role = RolesAuthoritiesInterfaces.Role;
import RoleStatus = RolesAuthoritiesInterfaces.RoleStatus;
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-current-role',
  templateUrl: './current-role.component.html',
  styleUrls: ['./current-role.component.scss']
})
export class CurrentRoleComponent implements OnInit{
  role:Role|undefined = undefined;
  roleStatus: string='';
  labelControl:FormControl;
  descriptionControl:FormControl;
  roleForm: FormGroup;
  constructor(private eventBus:EventBusService) {
    this.labelControl = new FormControl<string>('', [Validators.required]);
    this.descriptionControl = new FormControl<string>('',[Validators.required]);
    this.roleForm = new FormGroup<any>({
      labelControl: this.labelControl,
      descriptionControl: this.descriptionControl
    })
  }
  ngOnInit(): void {
    this.eventBus.on(RolesEvents.ROLE_SELECT.toString(),(value:any)=>{
      this.role = value.node;
      this.labelControl.setValue(this.role?.label);
      this.descriptionControl.setValue(this.role?.description);
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
}
