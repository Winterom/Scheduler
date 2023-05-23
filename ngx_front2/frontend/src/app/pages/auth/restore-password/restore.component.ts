import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthMessage} from "../../../messages/AuthMessages";
import {MessageService} from "primeng/api";
import {RestoreService} from "./restore.service";
import addErrorMessage = AuthMessage.addErrorMessage;

@Component({
  selector: 'app-reset',
  templateUrl: './restore.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class RestoreComponent {
  loading: boolean = false;
  emailControl: FormControl;
  form: FormGroup;
  resultSuccess:boolean=false;
  responseEmail:string = '';

  constructor(private router: Router,
              private restoreService: RestoreService,
              private messageService: MessageService) {
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.form = new FormGroup<any>({
      "email": this.emailControl
    })
  }
  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {
    this.loading = true;
    this.emailControl.markAsTouched();
    if (this.emailControl.hasError('email')) {
      console.log("invalid")
      addErrorMessage(this.messageService,'Введен не корректный email');
    }
    if (this.emailControl.hasError('required')) {
      console.log("netu")
      addErrorMessage(this.messageService,'Email не введен')
    }
    if (this.emailControl.invalid) {
      this.loading = false;
      return;
    }
    this.restoreService.sendAccountEmail(this.emailControl.value).subscribe({next:data =>{
        console.log(data);
        this.responseEmail = data.message;
        this.loading=false;
        this.resultSuccess =true;
      },error:err => {
      console.log(err);
        this.loading=false;
        addErrorMessage(this.messageService,err.error.message)
      }})
  }
}
