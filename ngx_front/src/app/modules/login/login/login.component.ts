import { Component, OnInit } from '@angular/core';
import {InputDefinition} from "../../../uikit/input/InputDefinition";
import {ButtonDefinition} from "../../../uikit/button/ButtonDefinition";



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public authTokenInput:InputDefinition = new InputDefinition();
  public passwordInput:InputDefinition = new InputDefinition();
  public buttonDefinition:ButtonDefinition = new ButtonDefinition();
  constructor() {

  }

  ngOnInit(): void {
    this.authTokenInput.label="Email или Телефон";
    this.authTokenInput.placeholder ="email или телефон";
    this.passwordInput.label= "Пароль";
    this.passwordInput.placeholder="Пароль";
    this.buttonDefinition.label="Войти";
    this.buttonDefinition.background ="#0E74B0";
    this.buttonDefinition.backgroundHover = "#387ca4";
    this.buttonDefinition.onClick=()=>{
        this.submitForm();
    }
  }
    submitForm(){

    }
}
