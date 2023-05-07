import { CanActivateFn } from '@angular/router';


export const desktopGuard: CanActivateFn = (route, state) => {
  return true;
};
