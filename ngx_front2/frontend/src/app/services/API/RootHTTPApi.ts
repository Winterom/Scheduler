import {RootApi} from "./RootApi";
import {HttpHeaders} from "@angular/common/http";

export class RootHTTPApi extends RootApi{
  readonly protocol: string='http';
}
export const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
