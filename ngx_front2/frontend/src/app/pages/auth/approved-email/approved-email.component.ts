import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ApprovedEmailService} from "./approved-email.service";

@Component({
  selector: 'app-approved-email',
  templateUrl: './approved-email.component.html',
  styleUrls: ['../login.scss'],
})
export class ApprovedEmailComponent implements OnInit{
  private readonly SUCCESS_RESULT:string='Почта успешно подтверждена';
  private readonly ERROR_RESULT:string='Токен подтверждения устарел';
  private readonly BAD_REQUEST:string='Не удалось выделить параметры в запросе';
  message:string=this.BAD_REQUEST;
  constructor(private router: Router,
              private approvedEmailService:ApprovedEmailService,
              private activateRoute: ActivatedRoute) {

  }
  ngOnInit(): void {
    const token = this.activateRoute.snapshot.queryParams['token'];
    const email = this.activateRoute.snapshot.queryParams['email'];
    if(email!==null&&email!==undefined&&token!==null&&token!==undefined){
      this.approvedEmailService.approvedEmail(token,email).subscribe({
      next:()=>{
        this.message=this.SUCCESS_RESULT;
      }
      ,error:() => {
          this.message=this.ERROR_RESULT;
        }
      })
    }else {
      this.message=this.BAD_REQUEST;
    }

  }

}
