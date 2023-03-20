import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function passwordStrangeValidator(passRequirement:PasswordStrangeRequirement):ValidatorFn{
  return (control:AbstractControl) : ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    const pattern = [
      `(?=([^a-z]*[a-z])\{${passRequirement.passwordMinLowerCase},\})`,
      `(?=([^A-Z]*[A-Z])\{${passRequirement.passwordMinUpperCase},\})`,
      `(?=([^0-9]*[0-9])\{${passRequirement.passwordMinNumber},\})`,
      `(?=(\.\*[\$\@\$\!\%\*\?\&])\{${passRequirement.passwordMinSymbol},\})`,
      `[A-Za-z\\d\$\@\$\!\%\*\?\&\.]{${
        passRequirement.passwordMinCharacters
      },}`
    ]
      .map(item => item.toString())
      .join("");
    const  resultValidation = RegExp(pattern).test(value);
    return !resultValidation ? {passwordStrange: true} : null;
  }
}

export class PasswordStrangeRequirement {
  public passwordMinLowerCase:number =1;
  public passwordMinNumber: number =1;
  public passwordMinSymbol:number =2;
  public passwordMinUpperCase: number =1;
  public passwordMinCharacters: number =8;
}
