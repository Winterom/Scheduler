import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {PermissionsService} from "../services/permissions.service";


export const isAuthentication: CanActivateFn = (route, state) => {
  return inject(PermissionsService).canActivateDesktop();
};
