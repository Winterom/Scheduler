import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _id: string|null=null;
  private _name: string|null=null;
  private _surname: string|null=null;
  private _lastname: string|null=null;
  private _email: string|null=null;
  private _token:string|null=null;
  private _isAuth:boolean=false;
  private _authorities:Set<string>|null = null;
  private _tokenExpire:Date|null = null;



  get id(): string | null {
    return this._id;
  }

  set id(value: string | null) {
    this._id = value;
  }

  get name(): string | null {
    return this._name;
  }

  set name(value: string | null) {
    this._name = value;
  }

  get surname(): string | null {
    return this._surname;
  }

  set surname(value: string | null) {
    this._surname = value;
  }

  get lastname(): string | null {
    return this._lastname;
  }

  set lastname(value: string | null) {
    this._lastname = value;
  }

  get email(): string | null {
    return this._email;
  }

  set email(value: string | null) {
    this._email = value;
  }

  get token(): string | null {
    if(this._tokenExpire!==null){
      if(this._tokenExpire?.getTime()>Date.now()){
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
  set token(value: string|null) {
    console.log('value '+value)
    if(value===null){
      this.clear();
      return;
    }
    this._token = value;
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
