import {Message} from "primeng/api";

export namespace LoginMessage{
  export class SuccessLoginMessage implements Message{
    severity='success';
    summary='OK';
    detail='Аутентификация выполнена'
  }
  export class ErrorLoginMessage implements Message {
    severity='error';
    summary='Ошибка';
    detail=''
  }
}

