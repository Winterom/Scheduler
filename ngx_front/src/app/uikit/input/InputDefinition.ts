import {FormControl} from "@angular/forms";


export class InputDefinition{
  public label:string = "";
  private _placeholder:string="";
  public control:FormControl = new FormControl<string>('');
  public type:InputType = InputType.TEXT;
  public id:string = this.getId();

  getId():string {
    return crypto.randomUUID();
  }

  set placeholder(value: string) {
    this._placeholder = value;
  }

  get placeholder(): string {
    if(this._placeholder===""){
      return this.label;
    }
    return this._placeholder;
  }
}

export enum InputType{
  PASSWORD='password', DATE='date',NUMBER='number',TEXT='text'
}

