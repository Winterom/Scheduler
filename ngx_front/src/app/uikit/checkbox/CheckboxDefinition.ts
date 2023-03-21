export class CheckboxDefinition{
  public label:string;
  public id:string;
  public onChange=()=>{};
  public state: boolean=false;

  constructor(id:string,label:string) {
    this.id=id;
    this.label=label;
  }
}
