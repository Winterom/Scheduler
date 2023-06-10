import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RegistrationService} from "./registration.service";
import {PasswordStrengthRequirement} from "../../../types/PasswordStrengthRequirement";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {passwordStrangeValidator} from "../../../validators/PasswordStrangeValidator";
import {checkIfMatchingPasswords} from "../../../validators/MatchingPasswordsValidator";
import {CustomMessage} from "../../../shared/messages/CustomMessages";
import {MessageService} from "primeng/api";
import addErrorMessage = CustomMessage.addErrorMessage;
import addSuccessMMessage = CustomMessage.addSuccessMessage;


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login.scss'],
  providers:[MessageService]
})
export class RegistrationComponent implements OnInit {
  loading: boolean = false;
  pswStrangeReq: PasswordStrengthRequirement|undefined;
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
      addErrorMessage(this.messageService,'Введен не корректный email', null)
      hasError = true;
    }
    if (this.emailControl.hasError('required')) {
      addErrorMessage(this.messageService,'Email не введен', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.surnameControl.hasError('required')) {
      addErrorMessage(this.messageService,'Не введена фамилия пользователя', null)
      hasError = true;
    }
    if (this.surnameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в фамилии пользователя', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.nameControl.hasError('required')) {
      addErrorMessage(this.messageService,'Не введено имя пользователя', null)
      hasError = true;
    }
    if (this.nameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в имени пользователя', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.lastnameControl.hasError('pattern')) {
      addErrorMessage(this.messageService,'Недопустимые символы в отчестве пользователя', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.passwordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Введите пароль', null)
      hasError = true;
    }
    if (this.passwordControl.hasError('passwordStrange')) {
      addErrorMessage(this.messageService,'Пароль не соответствует требованиям', null)
      hasError = true;
    }
    /***************************************************************************/
    if (this.confirmPasswordControl.hasError('required')) {
      addErrorMessage(this.messageService,'Повторите пароль', null)
      hasError = true;
    }
    if (this.registrationForm.hasError('match_error')) {
      addErrorMessage(this.messageService,'Пароли не совпадают', null)
      hasError = true;
    }
    if (hasError) {
      this.loading = false;
      return;
    }
    const rawPhone:string = this.phoneControl.value;
    const phone = rawPhone.replaceAll('-','')
      .replace('(','')
      .replace(')','')
      .replace(' ','');
    this.regService.registration(
      this.emailControl.value,
      phone,
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
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
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
        addSuccessMMessage(this.messageService,'email введен правильно и свободен',null);
        this.emailControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.emailControl.setErrors({emailBusy:true});
      }
    })
  }
  checkPhone() {
    if (this.phoneControl.errors != null||this.phoneControl.value===0) {
      return;
    }
    let rawPhone: string = this.phoneControl.value.toString();
    const phone = rawPhone.replaceAll('-','')
      .replace('(','')
      .replace(')','')
      .replace(' ','');
    console.log(phone);
    this.regService.checkPhone(phone).subscribe({
      next: () => {
        addSuccessMMessage(this.messageService,'Телефон введен правильно и свободен',null)
        this.phoneControl.updateValueAndValidity();
      },error:err=>{
        const errorMessages: string [] = err.error.messages
        errorMessages.forEach((value)=>{
          addErrorMessage(this.messageService,value,err.error.statusCode);
        })
        this.phoneControl.setErrors({phoneBusy:true});
      }
    })
  }
}
