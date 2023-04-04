import {RootWSApi} from "../../../../services/API/RootWSApi";

export class EmailsWSApi extends RootWSApi{
  private readonly emailList:string = '/emails';


  getEmailsListApi():string{
    const url =super.getDestinationPrefix()+this.emailList;
    console.log(url);
    return url;
  }
}
