import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS, HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError} from 'rxjs/operators';
import {UserService} from "../services/user.service";
import {UsersAPI} from "../services/API/UsersAPI";

const TOKEN_HEADER_KEY = 'Authorization';
@Injectable()
export class JwtTokenInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  constructor(private user:UserService,private userApi:UsersAPI) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<Object>> {
    let authReq = req;
    const token = this.user.token;
    if (token != null) {
      authReq = this.addTokenHeader(req, token);
    }else {
      console.log('токен нул')
    }

    return next.handle(authReq).pipe(
      catchError((error) => {
        console.log(authReq.url)
        if (
          error instanceof HttpErrorResponse &&
          !authReq.url.includes(this.userApi.auth_api) &&
          error.status === 401
        ) {
          return this.handle401Error(authReq, next);
        }
        return throwError(() => error);
      })
    );
  }
  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
        this.isRefreshing = true;
        console.log('refreshing!!!!')
        this.isRefreshing=false;
      }
    return next.handle(request);
  }
  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token)});
  }
}
export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: JwtTokenInterceptor, multi: true }
];
