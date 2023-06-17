import {Data} from "@angular/router";
import {ChronosUtils} from "../../shared/ChronosUtils";


export namespace UserWSInterface {

  import Chronos = ChronosUtils.Chronos;

  export class UserProfile {
    id: string ='';
    status: string ='';
    email: string ='';
    isEmailVerified:boolean = false;
    phone:string  ='';
    isPhoneVerified:boolean = false;
    createdAt:Data = new Date();
    credentialExpiredTime:Data = new Date();
    updatedAt:Data = new Date();
    roles:Role[] = [];
    emailVerifyToken:SendVerifyToken={nextTokenAfter:0}
  }
  export class Role{
    title:string = '';
    description:string = '';
  }
  export interface SendVerifyToken {
    nextTokenAfter:number;
  }
  export interface CheckEmailOrPhoneWSResponse{
    isBusy:boolean;
    param:string;
  }
  export enum EUserStatus {
    NEW_USER ='Новый пользователь',
    ACTIVE='Активный',
    BANNED='Заблокирован',
    DISMISSED='Уволен',
    DELETED='Аккаунт удален'
  }
}
