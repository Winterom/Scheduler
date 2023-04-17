import {SORT_DIRECTION} from "../../../../uikit/table/TableDefinition";

export interface AllEmailsTable{
  headers:ColumnSorting;
  rows:Set<RowEmailTable>;
}

interface RowEmailTable{
  id:number;
  type:string;
  isEnabled:boolean;
  description:string;
  alias:string;
  email:string;
}

interface ColumnSorting{
  column:string;
  direction:SORT_DIRECTION;
}
