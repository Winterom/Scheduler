import {Component, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-delete-role',
  templateUrl: './delete-role.component.html',
  styleUrls: ['./delete-role.component.scss']
})
export class DeleteRoleComponent implements OnInit{
  stepItems: MenuItem[] =[];
  activeIndex: number = 0;
  disabledNextBtn: boolean =false;
  disabledBackBtn: boolean = true;

  ngOnInit() {
    this.stepItems = [{label: 'Правила удаления'},
      {label: 'Контроль'},
      {label: 'Удаление'},];
  }

  onActiveIndexChange($event: any) {
    this.activeIndex = $event;
  }

  nextStep() {
      if(this.activeIndex===0){
        this.activeIndex = 1;
        this.disabledBackBtn =false;
      }else {
        this.activeIndex=2;
      }

  }

  backStep() {
    if(this.activeIndex===1){
      this.activeIndex=0;
      this.disabledBackBtn=true;
    }
  }

  deleteRole() {

  }
}
