import {RootWSApi} from "../../../../services/API/RootWSApi";
import {RootHTTPApi} from "../../../../services/API/RootHTTPApi";

export class EmailsWSApi extends RootHTTPApi{
  private readonly emailList:string = '/emails/table';


  getEmailsListApi():string{
    return super.getRoot() + this.emailList;
  }
}
