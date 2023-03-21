import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";

export class LoginButtonDefinition implements ButtonDefinition{
  public background: string="#0E74B0";
  public backgroundHover: string="#387ca4";
  public label: string="Войти";
  public onClick():void{};
}
