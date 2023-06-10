import {Component, EventEmitter, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {checkIfMatchingPasswords} from "../../../../../validators/MatchingPasswordsValidator";
import {PasswordStrengthRequirement} from "../../../../../types/PasswordStrengthRequirement";
import {ResponseStatus, WebsocketService} from "../../../../../services/ws/websocket";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {WSResponseEvents} from "../../../../../services/ws/websocket/WSResponseEvents";
import {WSRequestEvents} from "../../../../../services/ws/websocket/WSRequestEvents";


@Component({
  selector: 'app-change-psw',
  templateUrl: './change-psw.component.html',
})
export class ChangePswComponent implements OnInit{
  passwordControl: FormControl;
  confirmPasswordControl: FormControl;
  passwordChangeForm:FormGroup;
  loadingPasswordChangeForm: boolean=false;
  pswStrangeReq:PasswordStrengthRequirement|undefined;
  onError:EventEmitter<string>|undefined;
  constructor(
              private wsService:WebsocketService,
              private config:DynamicDialogConfig,
              private pswFormRef: DynamicDialogRef) {
    this.passwordControl = new FormControl<string>('', [Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('', Validators.required);
    this.passwordChangeForm = new FormGroup<any>({
      passwordControl: this.passwordControl,
      confirmPasswordControl: this.confirmPasswordControl
    },[checkIfMatchingPasswords(this.passwordControl, this.confirmPasswordControl)])
  }

  ngOnInit(): void {
    this.pswStrangeReq = this.config.data.psw;
    this.onError = this.config.data.onError;
    this.wsService.on<any>(WSResponseEvents.UPDATE_PASSWORD).subscribe({next:value=>{
        if(value.responseStatus===ResponseStatus.ERROR&&value.errorMessages!=null){
          value.errorMessages.forEach(x=>{this.onError?.emit(x)})
          this.loadingPasswordChangeForm = false;
          return;
        }
        if(value.responseStatus===ResponseStatus.OK){
          this.pswFormRef.close('Пароль успешно изменен')
          this.loadingPasswordChangeForm = false;
        }
      }});
  }
  submitPasswordFrm() {
    this.loadingPasswordChangeForm = true;
    let hasError = false;
    Object.keys(this.passwordChangeForm.controls).forEach(key => {
      this.passwordChangeForm.get(key)?.markAsTouched();
    });
    if (this.passwordControl.hasError('required')) {
      this.onError?.emit('Введите пароль');
      hasError = true;
    }
    if (this.passwordControl.hasError('passwordStrange')) {
      this.onError?.emit('Пароль не соответствует требованиям');
      hasError = true;
    }
    /***************************************************************************/
    if (this.confirmPasswordControl.hasError('required')) {
      this.onError?.emit('Повторите пароль');
      hasError = true;
    }
    if (this.passwordChangeForm.hasError('match_error')) {
      this.onError?.emit('Пароли не совпадают');
      hasError = true;
    }
    if (hasError) {
      this.loadingPasswordChangeForm = false;
      return;
    }
    const password:string = this.passwordControl.value;
    this.wsService.send(WSRequestEvents.UPDATE_PASSWORD,password);
  }



}
