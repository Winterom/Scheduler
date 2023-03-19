export class CheckboxDefinition{
  public label:string='';
  public id:string=this.getId();
  public onChange=()=>{};
  public state: boolean=false;

  getId():string {
    return crypto.randomUUID();
  }
}
