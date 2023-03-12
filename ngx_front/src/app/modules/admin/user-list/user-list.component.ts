import { Component, OnInit } from '@angular/core';
import {UsersTableDefinition} from "./UsersTableDefinition";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  table:UsersTableDefinition = new UsersTableDefinition();
  constructor() { }

  ngOnInit(): void {
  }

}
