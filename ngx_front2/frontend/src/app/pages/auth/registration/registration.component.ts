import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RegistrationService} from "./registration.service";
import {PasswordStrangeRequirement} from "../../../types/PasswordStrangeRequirement";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {passwordStrangeValidator} from "../../../validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../validators/MatchingPasswordsValidator";
import {AuthMessage} from "../../../messages/AuthMessages";
import {MessageService} from "primeng/api";
import addErrorMessage = AuthMessage.addErrorMessage;


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class RegistrationComponent implements OnInit {
  loading: boolean = false;
  pswStrangeReq: PasswordStrangeRequirement = new PasswordStrangeRequirement;
  registrationForm: FormGroup;
  passwordControl: FormControl;
  confirmPasswordControl: FormControl;
  emailControl: FormControl;
  nameControl: FormControl;
  surnameControl: FormControl;
  lastnameControl: FormControl;
  phoneControl: FormControl;
  resultSuccess:boolean=false;

  constructor(private router: Router,
              private messageService: MessageService,
              private regService: RegistrationService) {
    this.passwordControl = new FormControl<string>('', [Validators.required]);
    this.confirmPasswordControl = new FormControl<string>('', Validators.required);
    this.emailControl = new FormControl<string>('', [Validators.required, Validators.email]);
    this.nameControl = new FormControl<string>('', [Validators.required, Validators.pattern(/[А-Яа-яЁё]/)]);
    this.surnameControl = new FormControl<string>('', [Validators.required, Validators.pattern(/[А-Яа-яЁё]/)]);
    this.lastnameControl = new FormControl<string>('', [Validators.pattern(/[А-Яа-яЁё]/)]);
    this.phoneControl = new FormControl<number>(0);
    this.registrationForm = new FormGroup<any>({
      "newPassword": this.passwordControl,
      "confirmPassword": this.confirmPasswordControl,
      "email": this.emailControl,
      "name": this.nameControl,
      "surname": this.surnameControl,
      "lastname": this.lastnameControl,
      "phone": this.phoneControl
    }, [checkIfMatchingPasswords(this.passwordControl, this.confirmPasswordControl)])
  }

  ngOnInit(): void {
    this.regService.getPasswordRequirements().subscribe({
      next: data => {
        this.pswStrangeReq = data;
        this.passwordControl.addValidators(passwordStrangeValidator(this.pswStrangeReq))
      }
    })
  }

  returnToLoginPage() {
    this.router.navigate(['login']);
  }

  submitFrm() {
    this.loading = true;
    let hasError = false;
    Object.keys(this.registrationForm.controls).forEach(key => {
      this.registrationForm.get(key)?.markAsTouched();
    });
    /***************************************************************************/
    if (this.emailControl.hasError('email')) {
      addErrorMessage(this.messageService,'Введен не корректный email')
      hasError = true;
    }
    if (this.emailControl.hasError('required')) {
      addErrorMessage(this.messageService,'Email не введен')
      hasError = true;
    }
    /***************************************************************************/
    if (this.surnameControl.hasError('required')) {
      addErrorMessage(this.messageService,'Не введена фамилия пользователя')
      hasError = true;
    }
    if (this.surnameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в фамилии пользователя')
      hasError = true;
    }
    /***************************************************************************/
    if (this.nameControl.hasError('required')) {
      addErrorMessage(this.messageService,'Не введено имя пользователя')
      hasError = true;
    }
    if (this.nameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в имени пользователя')
      hasError = true;
    }
    /***************************************************************************/
    if (this.lastnameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в отчестве пользователя')
      hasError = true;
    }
    /***************************************************************************/
    if (this.passwordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Введите пароль')
      hasError = true;
    }
    if (this.passwordControl.hasError('passwordStrange')) {
      addErrorMessage(this.messageService,'Пароль не соответствует требованиям')
      hasError = true;
    }
    /***************************************************************************/
    if (this.confirmPasswordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Повторите пароль')
      hasError = true;
    }
    if (this.registrationForm.hasError('match_error')) {
      addErrorMessage(this.messageService,'Пароли не совпадают')
      hasError = true;
    }
    if (hasError) {
      this.loading = false;
      return;
    }
    this.regService.registration(
      this.emailControl.value,
      '+7'+this.phoneControl.value,
      this.surnameControl.value,
      this.nameControl.value,
      this.lastnameControl.value,
      this.passwordControl.value
    ).subscribe({
      next: () => {
        this.loading = false;
        this.resultSuccess=true;
      }, error: err => {
        this.loading = false;
        let message = new AuthMessage.ErrorLoginMessage;
        message.detail = err.error.message;
        this.messageService.add(message);
        this.resultSuccess=false;
      }
    })
  }

  checkEmail() {
    if (this.emailControl.errors != null) {
      return;
    }
    this.regService.checkEmail(this.emailControl.value).subscribe({
      next: () => {
        this.messageService.add(new AuthMessage.SuccessCheckEmail);
        this.emailControl.updateValueAndValidity();
      },error:err=>{
        addErrorMessage(this.messageService,err.error.message)
        this.emailControl.setErrors({emailBusy:true});
      }
    })
  }
  checkPhone() {
    if (this.phoneControl.errors != null||this.phoneControl.value===0) {
      return;
    }
    console.log('phone '+this.phoneControl.value);
    this.regService.checkPhone(this.phoneControl.value).subscribe({
      next: () => {
        this.messageService.add(new AuthMessage.SuccessCheckEmail);
        this.phoneControl.updateValueAndValidity();
      },error:err=>{
        addErrorMessage(this.messageService,err.error.message)
        this.phoneControl.setErrors({phoneBusy:true});
      }
    })
  }
}
