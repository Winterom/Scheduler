import {Message, MessageService} from "primeng/api";

export namespace CustomMessage{
  export class SuccessMessage implements Message{
    severity='success';
    summary='OK';
    detail=''
    public setTitle(msg:string){
      this.summary = this.summary+' '+msg;
    }
  }
  export class ErrorMessage implements Message {
    severity='error';
    summary='Ошибка';
    detail=''
    life=5000;

    public setTitle(msg:string){
      this.summary = this.summary+' '+msg;
    }
  }
  export const addErrorMessage=(service:MessageService|null, message:string, title:string| null)=>{
    if(service){
      const mes = new ErrorMessage();
      mes.detail = message;
      if(title!==null){
        mes.setTitle(title)
      }
      service.add(mes);
    }
  }
  export const addSuccessMessage = (service:MessageService|null, message:string, title:string| null)=>{
    if(service){
      const mes = new SuccessMessage();
      mes.detail = message;
      if(title!==null){
        mes.setTitle(title)
      }
      service.add(mes);
    }
  }
}

