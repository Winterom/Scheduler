import {SORT_DIRECTION, TableDefinition, VALUE_TYPE} from "../../../uikit/table/TableDefinition";

export class UsersTableDefinition extends TableDefinition{
  constructor() {
    super();
    super.headerDefinition=[
      {title: 'id', sort: SORT_DIRECTION.ASC,type:VALUE_TYPE.NUMBER,width:'5em'},
      {title: 'email', sort: SORT_DIRECTION.DESC,type:VALUE_TYPE.STRING,width:'100%'},
      {title:'Создан',sort:SORT_DIRECTION.ASC,type:VALUE_TYPE.DATE,width:'8em'},
      {title:'Изменен',sort:SORT_DIRECTION.ASC,type:VALUE_TYPE.DATE,width:'8em'}
    ];
    super.isCheckColumn=true;
  }
}


