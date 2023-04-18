export class TableDefinition{
  private _tableID:string=''
  private _isCheckColumn:boolean=false;
  private _headerDefinition:Map<string,TableHeaderColumnDefinition>= new Map<string, TableHeaderColumnDefinition>();
  private _rows:TableRows[]=[];

  get tableID(): string {
    return this._tableID;
  }

  set tableID(value: string) {
    this._tableID = value;
  }

  set headerDefinition(value: Map<string,TableHeaderColumnDefinition>) {
    this._headerDefinition = value;
  }

  get isCheckColumn(): boolean {
    return this._isCheckColumn;
  }

  set isCheckColumn(value: boolean) {
    this._isCheckColumn = value;
  }

  get headerDefinition():Map<string,TableHeaderColumnDefinition> {
    return this._headerDefinition;
  }

  get rows(): TableRows[] {
    return this._rows;
  }

  set rows(value: TableRows[]) {
    this._rows = value;
  }
}

export interface TableHeaderColumnDefinition{
  sort:SORT_DIRECTION;
  type:VALUE_TYPE;
  width:string;
  align:string;
}

export abstract class TableRows{
  abstract id:number;
  abstract cells:Map<TableHeaderColumnDefinition,string|number|boolean>
  getValueByHeader(header:TableHeaderColumnDefinition):string|number|boolean{
    const value = this.cells.get(header);
    if(value==undefined){
      return '';
    }else {
      return value;
    }
  }
}

export enum SORT_DIRECTION{
  ASC="asc",DESC="desc",NONE="none"
}
export enum VALUE_TYPE{
  STRING='string',NUMBER='number',DATE='date',BOOLEAN='boolean'
}

