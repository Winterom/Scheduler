import {Component, OnInit} from '@angular/core';
import {PrimeNGConfig} from "primeng/api";
import {TranslateService} from "@ngx-translate/core";
import {UserService} from "./services/user.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

 constructor(private config: PrimeNGConfig,
             private translateService: TranslateService,
             private user:UserService, private router: Router) {

 }

  ngOnInit(): void {
    this.translateService.setDefaultLang('ru');
    this.user.refreshing().subscribe({next:data=>{
        this.user.token=data.access_token;
        if(this.user.isAuth){
          this.router.navigate(['desktop']);
        }else {
          this.router.navigate(['login']);
        }
      },error:()=>{
        this.router.navigate(['login']);
      }})
  }
  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }
}
