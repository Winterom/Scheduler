
export interface AllEmailsTable{
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

