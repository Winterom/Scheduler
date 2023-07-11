import {TreeNode} from "primeng/api";
import {UserInterfaces} from "../user/UserInterfaces";

export namespace RolesAuthoritiesInterfaces {
  import EUserStatus = UserInterfaces.EUserStatus;

  export interface RolesGroup{
    roles:Role[];
  }
  export interface Role extends TreeNode{
    isCatalog:boolean;
    parentId:number;
    description:string;
    status:RoleStatus;
    createdAt:string;
    updatedAt:string;
    modifyBy:string;
    path: string
  }

  export enum RoleStatus{
    ACTIVE = 'ACTIVE',
    DELETE = 'DELETE',
    PASSED = 'PASSED'
  }

  export interface SelectedStatus{
    name: string;
    code: RoleStatus|null;
  }

  export interface Authorities {
    authorities: AuthorityNode[]
  }

  export interface AuthorityNode extends TreeNode{
    isCatalog:boolean;
    parentId:number;
    description:string;
  }

  export interface DragDropRole{
    newParentId: string;
    roleId: string;
  }

  export interface CheckedRoleForDelete{
    catalogCount: string;
    roleCount:string;
    roleAssignedUsers:CheckedRole[];

  }

  export interface CheckedRole{
    key:string;
    label:string
    isCatalog:boolean;
    parentId:number;
    status:RoleStatus;
    path: string;
    users:UserForCheckedRole[];
  }

  export interface UserForCheckedRole{
    key:string;
    email:string;
    status:EUserStatus;
  }
}
