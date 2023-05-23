import {Message, MessageService} from "primeng/api";

export namespace AuthMessage{
  export class SuccessLoginMessage implements Message{
    severity='success';
    summary='OK';
    detail='Аутентификация выполнена'
  }
  export class SuccessCheckEmail extends SuccessLoginMessage{
    constructor() {
      super();
      super.detail = 'email введен правильно и свободен';
    }
  }
  export class ErrorLoginMessage implements Message {
    severity='error';
    summary='Ошибка';
    detail=''
    life=5000;
  }
  export const addErrorMessage=(service:MessageService, message:string)=>{
    const mes = new ErrorLoginMessage();
    mes.detail = message;
    service.add(mes);
  }
}

