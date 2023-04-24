export class TableToolbarDefinition {
    public buttons:Map<EToolbarButton,TBButton> =new Map;
}

export enum EToolbarButton{
  ADD,DELETE,EDIT
}

export class TBButton{
  label:string='';
  action=()=>{};
}
