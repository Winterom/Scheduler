import {FormControl} from "@angular/forms";


export class InputDefinition{
  public label:string = "";
  public placeholder:string = "";
  public control:FormControl = new FormControl<any>(this.placeholder)
  public type:InputType = InputType.TEXT;
  public id:string = this.getId();

  getId():string {
    return crypto.randomUUID();
  }
}

export enum InputType{
  PASSWORD='password', DATE='date',NUMBER='number',TEXT='text'
}

