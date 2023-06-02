import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TranslateModule} from "@ngx-translate/core";
import {LoginModule} from "./pages/auth/login.module";
import {HttpClientModule} from "@angular/common/http";
import {DesktopModule} from "./pages/desktop/desktop.module";
import {authInterceptorProviders} from "./intercepters/jwt-token.interceptor";





@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    LoginModule,
    HttpClientModule,
    DesktopModule,
    TranslateModule.forRoot(),
  ],
  providers: [authInterceptorProviders],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


