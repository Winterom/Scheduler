import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TranslateModule} from "@ngx-translate/core";
import {LoginModule} from "./pages/auth/login.module";
import {HttpClientModule} from "@angular/common/http";
import {DesktopModule} from "./pages/desktop/desktop.module";
import {authInterceptorProviders} from "./intercepters/jwt-token.interceptor";
import {registerLocaleData} from "@angular/common";
import localeRu from '@angular/common/locales/ru';
import {SharedModule} from "./shared/shared.module";


registerLocaleData(localeRu, 'ru');


@NgModule({
  declarations: [
    AppComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        LoginModule,
        HttpClientModule,
        DesktopModule,
        TranslateModule.forRoot(),
        SharedModule,
    ],
  providers: [authInterceptorProviders,
    { provide: LOCALE_ID, useValue: 'ru' }
  ],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


