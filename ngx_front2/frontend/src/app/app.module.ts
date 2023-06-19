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
import {ProfileModule} from "./pages/profile/profile.module";
import { RolesAndAuthoritiesComponent } from './pages/roles-and-authorities/roles-and-authorities.component';
import {SharedModule} from "./shared/shared.module";
import {RolesAndAuthoritiesModule} from "./pages/roles-and-authorities/roles-and-authorities.module";
import { UsersListComponent } from './pages/users-list/users-list.component';
import {UsersListModule} from "./pages/users-list/users-list.module";


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


