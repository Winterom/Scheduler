export class SelectDefinition{
  rows: Set<SelectRow> = new Set<SelectRow>().add({id:'none',title:''});

}
export interface SelectRow{
  id:string;
  title:string;
}
