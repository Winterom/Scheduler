import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";
import {PasswordStrengthRequirement} from "../types/PasswordStrengthRequirement";


export function passwordStrangeValidator(passRequirement:PasswordStrengthRequirement):ValidatorFn{
  return (control:AbstractControl) : ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    console.log(passRequirement)
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
    console.log(pattern);
    const  resultValidation = RegExp(pattern).test(value);
    return !resultValidation ? {passwordStrange: true} : null;
  }
}


