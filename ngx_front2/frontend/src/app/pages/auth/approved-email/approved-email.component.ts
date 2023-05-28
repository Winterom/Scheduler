import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {ApprovedEmailService} from "./approved-email.service";
import {AuthMessage} from "../../../messages/AuthMessages";
import ErrorLoginMessage = AuthMessage.ErrorLoginMessage;

@Component({
  selector: 'app-approved-email',
  templateUrl: './approved-email.component.html',
  styleUrls: ['../login.scss'],
})
export class ApprovedEmailComponent implements OnInit{
  state: 'error'|'complete'|'loading'= 'loading';


  constructor(private router: Router,
              private approvedEmailService:ApprovedEmailService,
              private activateRoute: ActivatedRoute) {

  }
  ngOnInit(): void {
    const token = this.activateRoute.snapshot.queryParams['token'];
    const mail = this.activateRoute.snapshot.queryParams['mail'];
    if(mail!==null&&mail!==undefined&&token!==null&&token!==undefined){
      this.approvedEmailService.approvedEmail(token,mail).subscribe({
      next:data=>{
        this.state='complete';
      }
      ,error:err => {
          this.state='error';
        }
      })
    }

  }

}
