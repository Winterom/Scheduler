import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _id: String|null=null;
  private _name: String|null=null;
  private _surname: String|null=null;
  private _lastname: String|null=null;
  private _email: String|null=null;
  private _token:String|null=null;
  private _isAuth:boolean=false;
  private _authorities:Set<string>|null = null;
  private _tokenExpire:Date|null = null;
  constructor() {
  }


  get id(): String | null {
    return this._id;
  }

  set id(value: String | null) {
    this._id = value;
  }

  get name(): String | null {
    return this._name;
  }

  set name(value: String | null) {
    this._name = value;
  }

  get surname(): String | null {
    return this._surname;
  }

  set surname(value: String | null) {
    this._surname = value;
  }

  get lastname(): String | null {
    return this._lastname;
  }

  set lastname(value: String | null) {
    this._lastname = value;
  }

  get email(): String | null {
    return this._email;
  }

  set email(value: String | null) {
    this._email = value;
  }

  get token(): String | null {
    if(this._tokenExpire!==null){
      if(this._tokenExpire?.getTime()<Date.now()){
        return this._token;
      }else {
        this.clear();
      }
    }
    return null;
  }

 get isAuth():boolean{
    return this._isAuth;
 }
  set token(value: any) {
    this._token = value;
    if(value===null){
      this.clear();
      return;
    }
    try {
      const base64Url = value.split('.')[1];
      const base64 = base64Url.replace('-', '+').replace('_', '/');
      const rawToken = JSON.parse(this.decode(base64));
      this.id = rawToken.id;
      this.email = rawToken.sub;
      this._authorities = new Set<string>(rawToken.authorities);
      this._tokenExpire = new Date(rawToken.exp*1000);
      this._isAuth = true;
      return;
    }catch (e){
      this._isAuth=false;
      return;
    }

  }

  private clear(){
    this._id = null;
    this._name = null;
    this._surname = null;
    this._lastname = null;
    this._email = null;
    this._token = null;
  }
  private decode(rawToken:string):string{
    return atob(rawToken);
  }
}
