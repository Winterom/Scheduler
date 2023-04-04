import {RootApi} from "./RootApi";

export class RootWSApi extends RootApi{
  private postfix:string = 'ws';
  private applicationDestinationPrefixes:string='app';
  protocol: string ='ws';

  getWSRootApi():string{
    return super.getRoot()+'/'+this.postfix;
  }
  getDestinationPrefix():string{
    return '/'+ this.applicationDestinationPrefixes;
  }


}
