import {Component, OnInit} from '@angular/core';
import {PrimeNGConfig} from "primeng/api";
import {TranslateService} from "@ngx-translate/core";
import {UserService} from "./services/user.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

 constructor(private config: PrimeNGConfig,
             private translateService: TranslateService,
             private user:UserService,
             private router: Router,
             private activatedRoute:ActivatedRoute) {

 }

  ngOnInit(): void {
    this.translateService.setDefaultLang('ru');
    const url = this.router.url;
    console.log('url: '+url);
    console.log(this.activatedRoute)
    this.user.refreshing().subscribe({next:data=>{
        this.user.token=data.access_token;
        if(this.user.isAuth){
          this.router.navigate(['desktop']);
        }
      }})
  }
  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }
}
