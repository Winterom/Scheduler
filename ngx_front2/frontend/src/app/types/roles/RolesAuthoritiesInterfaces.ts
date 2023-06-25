import {TreeNode} from "primeng/api";

export namespace RolesAuthoritiesInterfaces {
  export interface RolesGroup{
    roles:Role[];
  }
  export interface Role extends TreeNode{
    isCatalog:boolean;
    parentId:number;
    description:string;
    createdAt:string;
    updatedAt:string;
    modifyBy:string;
  }
}
