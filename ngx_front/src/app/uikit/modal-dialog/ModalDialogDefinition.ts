export class ModalDialogDefinition{
  type:ModalDialogType = ModalDialogType.WARNING;
  header:string='';
  text:string='';
  closeButton:boolean=true;
}

export enum ModalDialogType{
  WARNING,INFORMATION,QUESTION_YES_NO
}
