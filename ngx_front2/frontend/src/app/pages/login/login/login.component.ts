import { Component } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  currentPage:CURRENT_PAGE=CURRENT_PAGE.LOGIN;
  loading: boolean = false;
  pswStrange:string='';
  protected readonly CURRENT_PAGE = CURRENT_PAGE;

  login() {

  }

  register() {

  }
  selectLoginPage(){
    this.currentPage=CURRENT_PAGE.LOGIN
  }
  selectRegisterPage(){
    this.currentPage=CURRENT_PAGE.REGISTER
  }
  selectRestorePage(){
    this.currentPage=CURRENT_PAGE.RESTORE
  }


}
enum CURRENT_PAGE{
  LOGIN,REGISTER,RESTORE
}


