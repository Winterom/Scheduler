import {FormControl} from "@angular/forms";


export class InputDefinition{
  public label:string = "";
  public control:FormControl = new FormControl<string>('');
  public type:InputType = InputType.TEXT;
  public id:string;
  constructor(id:string,label:string) {
    this.id=id;
    this.label=label;
  }

}

export enum InputType{
  PASSWORD='password', DATE='date',NUMBER='number',TEXT='text'
}

