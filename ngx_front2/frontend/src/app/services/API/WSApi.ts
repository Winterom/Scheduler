import {RootApi} from "./RootApi";

export class WSApi extends RootApi{
  protocol: string = 'ws';

  override getRoot(): string {
    return super.getRoot()+'/ws';
  }
}
