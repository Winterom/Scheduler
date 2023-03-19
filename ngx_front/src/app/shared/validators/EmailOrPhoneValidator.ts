import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";
/*Валидные телефонные номера
* {"2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125",
      "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"}
* */
export function emailOrPhoneValidator(): ValidatorFn{
  return (control:AbstractControl) : ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    const isEmail = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value);

    const isPhone = /^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$/.test(value);

    const valueValid = isEmail||isPhone;

    return !valueValid ? {notValid:true}: null;
  }
}
