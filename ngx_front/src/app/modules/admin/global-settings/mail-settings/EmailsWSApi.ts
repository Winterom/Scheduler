import {RootWSApi} from "../../../../services/API/RootWSApi";

export class EmailsWSApi extends RootWSApi{
  private readonly emailList:string = '/emails';


  getEmailsListApi():string{
    return super.getDestinationPrefix() + this.emailList;
  }
}
