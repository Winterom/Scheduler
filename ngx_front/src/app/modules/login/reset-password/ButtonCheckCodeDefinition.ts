import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";

export class ButtonCheckCodeDefinition implements ButtonDefinition{
  public background: string="#0E74B0";
  public backgroundHover: string="#387ca4";
  public label: string="Проверить";
  public onClick():void{};
}
