
export interface PasswordStrengthRequirement {
  minLowerCase:number;
  minNumber: number;
  minSymbol:number;
  minUpperCase: number ;
  minCharacters: number;
  passwordExpired: number;
  unit: string;
}
