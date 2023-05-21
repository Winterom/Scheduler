import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class User {
  private _id!: String;
  private name!: String;
  private surname!: String;
  private lastname!: String;
  private email!: String;
  private token!:String;
  constructor() {
  }
}
