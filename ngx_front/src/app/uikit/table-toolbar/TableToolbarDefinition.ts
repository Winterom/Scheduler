export class TableToolbarDefinition {
    public buttons:Set<TBButton> =new Set;
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
