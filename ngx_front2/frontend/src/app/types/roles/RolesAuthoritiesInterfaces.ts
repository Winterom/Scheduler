import {TreeNode} from "primeng/api";

export namespace RolesAuthoritiesInterfaces {
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

  export interface Authority{
    id: string;
    title: string;
    description: string;
  }
}
