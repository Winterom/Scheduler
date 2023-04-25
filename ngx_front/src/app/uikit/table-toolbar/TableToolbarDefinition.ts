export class TableToolbarDefinition {
    public buttons:Map<EToolbarButton,TBButton> =new Map;
}

export enum EToolbarButton{
  ADD,DELETE,EDIT, UPDATE
}

export class TBButton{
  label:string='';
  action=()=>{};
  image:boolean=false;

  constructor(label: string, action: () => void) {
    this.label = label;
    this.action = action;
  }
}
