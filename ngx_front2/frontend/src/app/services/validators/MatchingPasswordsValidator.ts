import {AbstractControl, ValidatorFn} from "@angular/forms";

export function checkIfMatchingPasswords(password: AbstractControl,
                                         matchingPassword: AbstractControl):ValidatorFn{
  return () => {
    if (password.value !== matchingPassword.value)
      return { match_error: 'Value does not match' };
    return null;
  };
}
