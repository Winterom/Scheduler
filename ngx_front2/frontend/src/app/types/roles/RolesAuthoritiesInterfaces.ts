export namespace RolesAuthoritiesInterfaces {
  export interface RolesGroup{
    roles:Roles[];
  }
  export interface Roles{
    id: number;
    isCatalog: boolean;
    parentId: number;
    title: string;
    description: string;
    createdAt: string;
    updatedAt: string;
    modifyBy: string; //email редактора
    roles:Roles[]|null;
  }
}
