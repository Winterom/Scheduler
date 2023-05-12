import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";
import {PasswordStrangeRequirement} from "../../types/PasswordStrangeRequirement";


export function passwordStrangeValidator(passRequirement:PasswordStrangeRequirement):ValidatorFn{
  return (control:AbstractControl) : ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    const pattern = [
      `(?=([^a-z]*[a-z])\{${passRequirement.minLowerCase},\})`,
      `(?=([^A-Z]*[A-Z])\{${passRequirement.minUpperCase},\})`,
      `(?=([^0-9]*[0-9])\{${passRequirement.minNumber},\})`,
      `(?=(\.\*[\$\@\$\!\%\*\?\&])\{${passRequirement.minSymbol},\})`,
      `[A-Za-z\\d\$\@\$\!\%\*\?\&\.]{${
        passRequirement.minCharacters
      },}`
    ]
      .map(item => item.toString())
      .join("");
    const  resultValidation = RegExp(pattern).test(value);
    return !resultValidation ? {passwordStrange: true} : null;
  }
}


