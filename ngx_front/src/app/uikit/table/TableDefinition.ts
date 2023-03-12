export class TableDefinition{
  private _isCheckColumn:boolean=false;
  private _headerDefinition:TableHeaderColumnDefinition[]= [];

  set headerDefinition(value: TableHeaderColumnDefinition[]) {
    this._headerDefinition = value;
  }

  get isCheckColumn(): boolean {
    return this._isCheckColumn;
  }

  set isCheckColumn(value: boolean) {
    this._isCheckColumn = value;
  }

  get headerDefinition(): TableHeaderColumnDefinition[] {
    if (!this._headerDefinition){
      return [];
    }
    return this._headerDefinition;
  }
}
export interface TableHeaderColumnDefinition{
  title:string;
  sort:SORT_DIRECTION;
  type:VALUE_TYPE;
  width:string;
}
export enum SORT_DIRECTION{
  ASC="asc",DESC="desc"
}
export enum VALUE_TYPE{
  STRING='string',NUMBER='number',DATE='date'
}

